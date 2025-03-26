package model;

/**
 * Represents a body segment of the snake.
 * Extends GameElement and follows the snake head.
 */
public class SnakeBody extends GameElement {

    public SnakeBody(int x, int y) {
        super(x, y);
    }

    /**
     * Move the body segment to a new position
     */
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}