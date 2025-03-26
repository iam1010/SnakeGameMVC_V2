package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Food;
import model.SnakeBody;
import model.SnakeHead;

/**
 * Panel for rendering the actual game.
 */
public class GamePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    // Game elements
    private SnakeHead snakeHead;
    private List<SnakeBody> snakeBody;
    private Food food;

    // Game state
    private boolean isGameOver;
    private boolean isPaused;
    private int score;

    // Colors
    private final Color BACKGROUND_COLOR = Color.BLACK;
    private final Color SNAKE_HEAD_COLOR = Color.GREEN;
    private final Color SNAKE_BODY_COLOR = new Color(0, 180, 0);
    private final Color FOOD_COLOR = Color.RED;
    private final Color GRID_COLOR = new Color(20, 20, 20);
    private final Color TEXT_COLOR = Color.WHITE;

    // Game board dimensions
    private int boardWidth;
    private int boardHeight;
    private int cellSize;

    /**
     * Constructor
     */
    public GamePanel() {
        setBackground(BACKGROUND_COLOR);
        boardWidth = 0;
        boardHeight = 0;
        cellSize = 0;
    }

    /**
     * Update game state before rendering
     */
    public void updateGameState(SnakeHead head, List<SnakeBody> body, Food food,
                                boolean gameOver, boolean paused, int score,
                                int boardWidth, int boardHeight, int cellSize) {
        this.snakeHead = head;
        this.snakeBody = body;
        this.food = food;
        this.isGameOver = gameOver;
        this.isPaused = paused;
        this.score = score;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.cellSize = cellSize;

        // Repaint the panel
        repaint();
    }

    /**
     * Paint the game
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the grid
        drawGrid(g2d);

        // Draw game elements
        if (snakeHead != null && snakeBody != null && food != null) {
            drawFood(g2d);
            drawSnake(g2d);
        }

        // Draw score
        drawScore(g2d);

        // Draw game over message
        if (isGameOver) {
            drawGameOver(g2d);
        }

        // Draw pause message
        if (isPaused && !isGameOver) {
            drawPaused(g2d);
        }
    }

    /**
     * Draw the grid
     */
    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(GRID_COLOR);

        // Draw vertical lines
        for (int x = 0; x <= boardWidth; x += cellSize) {
            g2d.drawLine(x, 0, x, boardHeight);
        }

        // Draw horizontal lines
        for (int y = 0; y <= boardHeight; y += cellSize) {
            g2d.drawLine(0, y, boardWidth, y);
        }
    }

    /**
     * Draw the snake
     */
    private void drawSnake(Graphics2D g2d) {
        // Draw body segments
        g2d.setColor(SNAKE_BODY_COLOR);
        for (SnakeBody segment : snakeBody) {
            int x = segment.getX() * cellSize;
            int y = segment.getY() * cellSize;
            g2d.fillRoundRect(x + 1, y + 1, cellSize - 2, cellSize - 2, 8, 8);
        }

        // Draw head
        g2d.setColor(SNAKE_HEAD_COLOR);
        int headX = snakeHead.getX() * cellSize;
        int headY = snakeHead.getY() * cellSize;
        g2d.fillRoundRect(headX + 1, headY + 1, cellSize - 2, cellSize - 2, 8, 8);

        // Draw eyes
        g2d.setColor(Color.BLACK);
        int eyeSize = cellSize / 5;
        g2d.fillOval(headX + cellSize / 4 - eyeSize / 2, headY + cellSize / 3 - eyeSize / 2, eyeSize, eyeSize);
        g2d.fillOval(headX + 3 * cellSize / 4 - eyeSize / 2, headY + cellSize / 3 - eyeSize / 2, eyeSize, eyeSize);
    }

    /**
     * Draw the food
     */
    private void drawFood(Graphics2D g2d) {
        g2d.setColor(FOOD_COLOR);
        int x = food.getX() * cellSize;
        int y = food.getY() * cellSize;
        g2d.fillOval(x + 2, y + 2, cellSize - 4, cellSize - 4);
    }

    /**
     * Draw the score
     */
    private void drawScore(Graphics2D g2d) {
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Score: " + score, 10, 25);
    }

    /**
     * Draw game over message
     */
    private void drawGameOver(Graphics2D g2d) {
        String message = "Game Over";
        String subMessage = "Press 'R' to restart or 'M' for menu";

        g2d.setColor(new Color(0, 0, 0, 150)); // Semi-transparent background
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));

        // Center the message
        FontMetrics fm = g2d.getFontMetrics();
        int messageWidth = fm.stringWidth(message);
        int messageX = (getWidth() - messageWidth) / 2;
        int messageY = getHeight() / 2;

        g2d.drawString(message, messageX, messageY);

        // Sub message
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        fm = g2d.getFontMetrics();
        int subMessageWidth = fm.stringWidth(subMessage);
        int subMessageX = (getWidth() - subMessageWidth) / 2;

        g2d.setColor(Color.WHITE);
        g2d.drawString(subMessage, subMessageX, messageY + 40);
    }

    /**
     * Draw pause message
     */
    private void drawPaused(Graphics2D g2d) {
        String message = "Paused";
        String subMessage = "Press 'P' to resume";

        g2d.setColor(new Color(0, 0, 0, 150)); // Semi-transparent background
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));

        // Center the message
        FontMetrics fm = g2d.getFontMetrics();
        int messageWidth = fm.stringWidth(message);
        int messageX = (getWidth() - messageWidth) / 2;
        int messageY = getHeight() / 2;

        g2d.drawString(message, messageX, messageY);

        // Sub message
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        fm = g2d.getFontMetrics();
        int subMessageWidth = fm.stringWidth(subMessage);
        int subMessageX = (getWidth() - subMessageWidth) / 2;

        g2d.setColor(Color.WHITE);
        g2d.drawString(subMessage, subMessageX, messageY + 40);
    }
}