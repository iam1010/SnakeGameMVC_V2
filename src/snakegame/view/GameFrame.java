package snakegame.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import snakegame.controller.GameController;

/**
 * Main frame for the Snake Game.
 * Contains all the game panels and manages switching between them.
 */
public class GameFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    // Constants
    private static final int DEFAULT_WIDTH = 615;
    private static final int DEFAULT_HEIGHT = 540;

    // Panels
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private HighScorePanel highScorePanel;

    // Controller
    private GameController controller;

    /**
     * Constructor
     */
    public GameFrame(String title) {
        super(title);

        // Setup frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null); // Center on screen

        // Initialize panels
        initPanels();

        // Set initial panel to menu
        showMenuPanel();
    }

    /**
     * Initialize all panels
     */
    private void initPanels() {
        // Create panels
        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        highScorePanel = new HighScorePanel();

        // Set panel layouts
        setLayout(new CardLayout());

        // Add panels to frame
        add(menuPanel, "MENU");
        add(gamePanel, "GAME");
        add(highScorePanel, "HIGHSCORES");
    }

    /**
     * Set the controller for this frame
     */
    public void setController(GameController controller) {
        this.controller = controller;

        // Add action listeners to menu buttons
        menuPanel.setPlayButtonListener(e -> controller.startNewGame());
        menuPanel.setHighScoreButtonListener(e -> controller.showHighScores());

        // Add action listeners to high score panel buttons
        highScorePanel.setBackButtonListener(e -> controller.showMenu());

        // Set key listener for game panel
        addKeyListener(controller.getKeyboardController());
    }

    /**
     * Show the menu panel
     */
    public void showMenuPanel() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "MENU");
        // Reset focus for keyboard input
        requestFocusInWindow();
    }

    /**
     * Show the game panel
     */
    public void showGamePanel() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "GAME");
        // Reset focus for keyboard input
        requestFocusInWindow();
    }

    /**
     * Show the high score panel
     */
    public void showHighScorePanel() {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "HIGHSCORES");
        // Reset focus for keyboard input
        requestFocusInWindow();
    }

    // Getters for panels

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public HighScorePanel getHighScorePanel() {
        return highScorePanel;
    }
}