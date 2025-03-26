package model;

import java.io.*;
import java.util.*;

/**
 * Main model class for the Snake Game.
 * Implements the game logic, including snake movement,
 * collision detection, and scoring.
 */
public class SnakeGame {
    // Game board dimensions
    private final int boardWidth;
    private final int boardHeight;
    private final int cellSize;

    // Game elements
    private SnakeHead snakeHead;
    private List<SnakeBody> snakeBody;
    private Food food;

    // Game state
    private boolean isGameOver;
    private boolean isPaused;
    private int score;
    private Direction currentDirection;
    private Direction nextDirection;

    // High scores
    private List<HighScore> highScores;
    private final String HIGH_SCORES_FILE = "highscores.dat";
    private final int MAX_HIGH_SCORES = 10;

    // Game speed
    private int gameSpeed;
    private static final int INITIAL_SPEED = 150; // milliseconds delay between updates
    private static final int SPEED_INCREASE = 5; // ms to decrease per level
    private static final int MIN_SPEED = 70; // fastest game speed

    /**
     * Direction enum for snake movement
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * Constructor to initialize the game
     */
    public SnakeGame(int width, int height, int cellSize) {
        this.boardWidth = width;
        this.boardHeight = height;
        this.cellSize = cellSize;

        highScores = new ArrayList<>();
        loadHighScores();

        resetGame();
    }

    /**
     * Initialize/reset the game state
     */
    public void resetGame() {
        // Initialize snake at the center of the board
        int startX = (boardWidth / cellSize) / 2;
        int startY = (boardHeight / cellSize) / 2;

        snakeHead = new SnakeHead(startX, startY);
        snakeBody = new ArrayList<>();

        // Add initial body segments
        for (int i = 0; i < 3; i++) {
            snakeBody.add(new SnakeBody(startX, startY + i + 1));
        }

        // Set initial direction and game state
        currentDirection = Direction.UP;
        nextDirection = Direction.UP;
        isGameOver = false;
        isPaused = false;
        score = 0;
        gameSpeed = INITIAL_SPEED;

        // Create first food
        spawnFood();
    }

    /**
     * Spawns a food item at a random location not occupied by the snake
     */
    private void spawnFood() {
        Random random = new Random();
        int maxX = boardWidth / cellSize;
        int maxY = boardHeight / cellSize;
        int foodX, foodY;
        boolean validPosition;

        do {
            foodX = random.nextInt(maxX);
            foodY = random.nextInt(maxY);
            validPosition = true;

            // Check if position is occupied by snake head or body
            if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
                validPosition = false;
            } else {
                for (SnakeBody segment : snakeBody) {
                    if (segment.getX() == foodX && segment.getY() == foodY) {
                        validPosition = false;
                        break;
                    }
                }
            }
        } while (!validPosition);

        food = new Food(foodX, foodY);
    }

    /**
     * Updates the game state for one game tick
     */
    public void update() {
        if (isGameOver || isPaused) {
            return;
        }

        // Update direction
        currentDirection = nextDirection;

        // Store previous positions for body segment updates
        int prevX = snakeHead.getX();
        int prevY = snakeHead.getY();

        // Calculate new head position
        int newX = prevX;
        int newY = prevY;

        switch (currentDirection) {
            case UP:
                newY = prevY - 1;
                break;
            case DOWN:
                newY = prevY + 1;
                break;
            case LEFT:
                newX = prevX - 1;
                break;
            case RIGHT:
                newX = prevX + 1;
                break;
        }

        // Handle wall wrapping (pass through walls)
        int maxX = boardWidth / cellSize;
        int maxY = boardHeight / cellSize;

        if (newX < 0) {
            newX = maxX - 1;  // Wrap to right edge
        } else if (newX >= maxX) {
            newX = 0;  // Wrap to left edge
        }

        if (newY < 0) {
            newY = maxY - 1;  // Wrap to bottom edge
        } else if (newY >= maxY) {
            newY = 0;  // Wrap to top edge
        }

        // Move snake head
        snakeHead.move(newX, newY);

        // Check for collisions
        if (checkCollisions()) {
            isGameOver = true;
            return;
        }

        // Check if snake has eaten food
        if (snakeHead.getX() == food.getX() && snakeHead.getY() == food.getY()) {
            eatFood();
        }

        // Move body segments
        int currX, currY;
        for (SnakeBody segment : snakeBody) {
            currX = segment.getX();
            currY = segment.getY();
            segment.move(prevX, prevY);
            prevX = currX;
            prevY = currY;
        }
    }

    /**
     * Handle food consumption
     */
    private void eatFood() {
        // Increase score
        score += 10;

        // Add new body segment at the end of the snake
        SnakeBody lastSegment = snakeBody.isEmpty() ?
                new SnakeBody(snakeHead.getX(), snakeHead.getY()) :
                snakeBody.get(snakeBody.size() - 1);

        snakeBody.add(new SnakeBody(lastSegment.getX(), lastSegment.getY()));

        // Increase game speed
        if (gameSpeed > MIN_SPEED) {
            gameSpeed = Math.max(MIN_SPEED, gameSpeed - SPEED_INCREASE);
        }

        // Spawn new food
        spawnFood();
    }

    /**
     * Check for collisions with self
     */
    private boolean checkCollisions() {
        int headX = snakeHead.getX();
        int headY = snakeHead.getY();

        // Check self-collision only
        for (SnakeBody segment : snakeBody) {
            if (headX == segment.getX() && headY == segment.getY()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Change the snake's direction
     */
    public void changeDirection(Direction newDirection) {
        // Prevent 180-degree turns
        if ((currentDirection == Direction.UP && newDirection == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && newDirection == Direction.UP) ||
                (currentDirection == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && newDirection == Direction.LEFT)) {
            return;
        }

        nextDirection = newDirection;
    }

    /**
     * Check if the current score qualifies for the high score list
     * @return true if the score qualifies as a high score, false otherwise
     */
    public boolean isHighScore() {
        return highScores.size() < MAX_HIGH_SCORES || score > highScores.get(highScores.size() - 1).getScore();
    }

    /**
     * Add a high score with the given player name
     * @param playerName The name of the player
     */
    public void addHighScore(String playerName) {
        if (isHighScore()) {
            highScores.add(new HighScore(playerName, score));

            // Sort high scores
            Collections.sort(highScores, (hs1, hs2) -> Integer.compare(hs2.getScore(), hs1.getScore()));

            // Trim list to max size
            if (highScores.size() > MAX_HIGH_SCORES) {
                highScores = highScores.subList(0, MAX_HIGH_SCORES);
            }

            // Save high scores
            saveHighScores();
        }
    }

    /**
     * Save high scores to a file
     */
    private void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGH_SCORES_FILE))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }

    /**
     * Load high scores from a file
     */
    @SuppressWarnings("unchecked")
    private void loadHighScores() {
        File file = new File(HIGH_SCORES_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                highScores = (List<HighScore>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading high scores: " + e.getMessage());
                highScores = new ArrayList<>();
            }
        } else {
            highScores = new ArrayList<>();
        }
    }

    // Getters and setters

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public int getScore() {
        return score;
    }

    public SnakeHead getSnakeHead() {
        return snakeHead;
    }

    public List<SnakeBody> getSnakeBody() {
        return snakeBody;
    }

    public Food getFood() {
        return food;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getCellSize() {
        return cellSize;
    }

    public List<HighScore> getHighScores() {
        return new ArrayList<>(highScores);
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    /**
     * Add a high score with the given name and score
     * (This overload is for manually adding scores, different from the current game score)
     * @param name The player's name
     * @param score The score value
     */
    public void addHighScore(String name, int score) {
        highScores.add(new HighScore(name, score));
        Collections.sort(highScores, (hs1, hs2) -> Integer.compare(hs2.getScore(), hs1.getScore()));
        if (highScores.size() > MAX_HIGH_SCORES) {
            highScores = highScores.subList(0, MAX_HIGH_SCORES);
        }
        saveHighScores();
    }
}