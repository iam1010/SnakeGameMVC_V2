package snakegame.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel for displaying the main menu.
 */
public class MenuPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton playButton;
    private JButton highScoresButton;
    private JLabel titleLabel;

    /**
     * Constructor
     */
    public MenuPanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    /**
     * Initialize components
     */
    private void initComponents() {
        // Create title label
        titleLabel = new JLabel("Snake Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.DARK_GRAY);

        // Create buttons
        playButton = new JButton("Play Game");
        highScoresButton = new JButton("High Scores");

        // Style buttons
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        highScoresButton.setFont(new Font("Arial", Font.BOLD, 20));

        // Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 20));
        buttonPanel.add(playButton);
        buttonPanel.add(highScoresButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 150, 150));

        // Add components to panel
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Set background color
        setBackground(new Color(230, 230, 230));
    }

    /**
     * Set listener for the play button
     */
    public void setPlayButtonListener(ActionListener listener) {
        playButton.addActionListener(listener);
    }

    /**
     * Set listener for the high scores button
     */
    public void setHighScoreButtonListener(ActionListener listener) {
        highScoresButton.addActionListener(listener);
    }
}