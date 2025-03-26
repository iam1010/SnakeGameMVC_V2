package model;

import java.io.Serializable;

/**
 * Represents a high score entry.
 */
public class HighScore implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private int score;
    private long timestamp;

    public HighScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        this.timestamp = System.currentTimeMillis();
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public long getTimestamp() {
        return timestamp;
    }
}