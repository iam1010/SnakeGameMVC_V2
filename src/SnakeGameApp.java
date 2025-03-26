import controller.GameController;
import view.GameFrame;

/**
 * Main class that launches the Snake Game application
 */
public class SnakeGameApp {
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure thread safety
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame("Snake Game");
            GameController gameController = new GameController(gameFrame);
            gameFrame.setController(gameController);
            gameFrame.setVisible(true);
        });
    }
}