package model;

/**
 * Abstract base class for game elements.
 * Provides common properties and methods.
 */
public abstract class GameElement {
    protected int x;
    protected int y;

    public GameElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}