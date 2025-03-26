package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import model.HighScore;

/**
 * Panel for displaying high scores.
 */
public class HighScorePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel titleLabel;
    private JPanel scoresPanel;
    private JButton backButton;

    /**
     * Constructor
     */
    public HighScorePanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    /**
     * Initialize components
     */
    private void initComponents() {
        // Create title label
        titleLabel = new JLabel("High Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.DARK_GRAY);

        // Create scores panel
        scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));
        scoresPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        // Create back button
        backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));

        // Add components to panel
        add(titleLabel, BorderLayout.NORTH);

        // Add scores panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(scoresPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Add back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);

        // Set background color
        setBackground(new Color(230, 230, 230));
    }

    /**
     * Update high scores display
     */
    public void updateHighScores(List<HighScore> highScores) {
        scoresPanel.removeAll();

        if (highScores.isEmpty()) {
            JLabel noScoresLabel = new JLabel("No high scores yet!");
            noScoresLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noScoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            scoresPanel.add(noScoresLabel);
        } else {
            // Add header
            JPanel headerPanel = new JPanel(new GridLayout(1, 3));
            headerPanel.add(new JLabel("Rank", JLabel.CENTER));
            headerPanel.add(new JLabel("Name", JLabel.CENTER));
            headerPanel.add(new JLabel("Score", JLabel.CENTER));
            headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            scoresPanel.add(headerPanel);
            scoresPanel.add(Box.createVerticalStrut(10));

            // Add scores
            for (int i = 0; i < highScores.size(); i++) {
                HighScore score = highScores.get(i);
                JPanel scorePanel = new JPanel(new GridLayout(1, 3));

                scorePanel.add(new JLabel("#" + (i + 1), JLabel.CENTER));
                scorePanel.add(new JLabel(score.getPlayerName(), JLabel.CENTER));
                scorePanel.add(new JLabel(String.valueOf(score.getScore()), JLabel.CENTER));

                scorePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                scoresPanel.add(scorePanel);
                scoresPanel.add(Box.createVerticalStrut(5));
            }
        }

        // Update UI
        scoresPanel.revalidate();
        scoresPanel.repaint();
    }

    /**
     * Set listener for the back button
     */
    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}