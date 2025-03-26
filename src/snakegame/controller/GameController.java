package snakegame.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import snakegame.model.SnakeGame;
import snakegame.model.SnakeGame.Direction;
import snakegame.view.GameFrame;
import snakegame.view.PlayerNameDialog;

/**
 * Main controller for the Snake Game.
 * Handles game flow and user input.
 */
public class GameController {
    // Model
    private SnakeGame game;

    // View
    private GameFrame gameFrame;

    // Game loop
    private Timer gameTimer;

    // Keyboard controller
    private KeyboardController keyboardController;

    // Constants
    private static final int CELL_SIZE = 20;
    private static final int BOARD_WIDTH = 600;
    private static final int BOARD_HEIGHT = 500;

    /**
     * Constructor
     */
    public GameController(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        // Initialize model
        game = new SnakeGame(BOARD_WIDTH, BOARD_HEIGHT, CELL_SIZE);

        // Initialize keyboard controller
        keyboardController = new KeyboardController();

        // Initialize game timer (using javax.swing.Timer)
        gameTimer = new Timer(game.getGameSpeed(), new GameLoop());
    }

    /**
     * Start a new game
     */
    public void startNewGame() {
        // Reset game state
        game.resetGame();

        // Update view
        updateGameView();

        // Show game panel
        gameFrame.showGamePanel();

        // Start game timer
        gameTimer.setDelay(game.getGameSpeed());
        gameTimer.start();
    }

    /**
     * Show high scores
     */
    public void showHighScores() {
        // Update high scores panel
        gameFrame.getHighScorePanel().updateHighScores(game.getHighScores());

        // Show high scores panel
        gameFrame.showHighScorePanel();
    }

    /**
     * Show menu
     */
    public void showMenu() {
        // Stop game timer if running
        if (gameTimer.isRunning()) {
            gameTimer.stop();
        }

        // Show menu panel
        gameFrame.showMenuPanel();
    }

    /**
     * Pause/resume the game
     */
    public void togglePause() {
        if (game.isGameOver()) {
            return;
        }

        if (game.isPaused()) {
            game.setPaused(false);
            gameTimer.start();
        } else {
            game.setPaused(true);
            gameTimer.stop();
        }

        // Update view
        updateGameView();
    }

    /**
     * Update game view with current game state
     */
    private void updateGameView() {
        gameFrame.getGamePanel().updateGameState(
                game.getSnakeHead(),
                game.getSnakeBody(),
                game.getFood(),
                game.isGameOver(),
                game.isPaused(),
                game.getScore(),
                game.getBoardWidth(),
                game.getBoardHeight(),
                game.getCellSize()
        );
    }

    /**
     * Game loop implementation
     */
    private class GameLoop implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Update game state
            game.update();

            // Update timer delay if game speed changed
            int currentDelay = gameTimer.getDelay();
            if (currentDelay != game.getGameSpeed()) {
                gameTimer.setDelay(game.getGameSpeed());
            }

            // Check if game is over
            if (game.isGameOver()) {
                gameTimer.stop();

                // Check if it's a high score and prompt for name
                if (game.isHighScore()) {
                    SwingUtilities.invokeLater(() -> {
                        String playerName = PlayerNameDialog.showDialog(gameFrame, game.getScore());
                        game.addHighScore(playerName);
                    });
                }
            }

            // Update view
            updateGameView();
        }
    }

    /**
     * Get keyboard controller
     */
    public KeyboardController getKeyboardController() {
        return keyboardController;
    }

    /**
     * Keyboard controller class
     */
    public class KeyboardController extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            // Direction controls
            switch (key) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    game.changeDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    game.changeDirection(Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    game.changeDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    game.changeDirection(Direction.RIGHT);
                    break;

                // Game controls
                case KeyEvent.VK_P:
                    togglePause();
                    break;
                case KeyEvent.VK_R:
                    if (game.isGameOver()) {
                        startNewGame();
                    }
                    break;
                case KeyEvent.VK_M:
                    showMenu();
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (game.isPaused() || game.isGameOver()) {
                        showMenu();
                    } else {
                        togglePause();
                    }
                    break;
            }
        }
    }
}