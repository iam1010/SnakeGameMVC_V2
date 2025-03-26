package snakegame.model;

/**
 * Represents the head of the snake.
 * Extends GameElement and adds snake-specific functionality.
 */
public class SnakeHead extends GameElement {

    public SnakeHead(int x, int y) {
        super(x, y);
    }

    /**
     * Move the snake head to a new position
     */
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}