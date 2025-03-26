package snakegame.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for entering player name after achieving a high score.
 */
public class PlayerNameDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JTextField nameField;
    private JButton submitButton;
    private boolean submitted = false;

    /**
     * Constructor
     */
    public PlayerNameDialog(JFrame parent, int score) {
        super(parent, "New High Score!", true);
        initComponents(score);

        // Set dialog properties
        setSize(350, 200);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    /**
     * Initialize components
     */
    private void initComponents(int score) {
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create message
        JLabel messageLabel = new JLabel("<html>Congratulations!<br>You achieved a high score of " + score + "!<br>Please enter your name:</html>");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create name field
        nameField = new JTextField(15);
        nameField.setText("Player");

        // Create submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getPlayerName().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(PlayerNameDialog.this,
                            "Please enter a name.",
                            "Name Required",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    submitted = true;
                    dispose();
                }
            }
        });

        // Add components to panel
        panel.add(messageLabel, BorderLayout.NORTH);

        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(new JLabel("Name: "));
        inputPanel.add(nameField);
        panel.add(inputPanel, BorderLayout.CENTER);

        panel.add(submitButton, BorderLayout.SOUTH);

        // Add panel to dialog
        add(panel);

        // Set focus to name field
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                nameField.requestFocus();
                nameField.selectAll();
            }
        });

        // Handle Enter key
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButton.doClick();
            }
        });
    }

    /**
     * Get the player name entered
     */
    public String getPlayerName() {
        return nameField.getText();
    }

    /**
     * Check if the dialog was submitted
     */
    public boolean isSubmitted() {
        return submitted;
    }

    /**
     * Show the dialog and return the player name
     */
    public static String showDialog(JFrame parent, int score) {
        PlayerNameDialog dialog = new PlayerNameDialog(parent, score);
        dialog.setVisible(true);

        if (dialog.isSubmitted()) {
            return dialog.getPlayerName();
        } else {
            return "Player"; // Default name if dialog is closed
        }
    }
}