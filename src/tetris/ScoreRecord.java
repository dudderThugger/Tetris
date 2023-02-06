package tetris;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Szelestey Adam
 * @see ScoreRecord class, stores the value of a game's score record
 */
public class ScoreRecord implements Serializable {
    /**
     * The nickname of the player who started the game
     */
    String name;
    /**
     * The time and date of the game that was finished
     */
    LocalDateTime time;
    /**
     * The score scored in the game
     */
    int score;

    /**
     * Constructor, sets the object's fields
     * @param name The value of the name field
     * @param score The value of the score field
     * @param time The value of the time field
     */
    public ScoreRecord(String name, int score, LocalDateTime time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    /**
     * @return The value of the name field in the record
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The value of the time field in the record
     */
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * @return The value of the score field in the record
     */
    public int getScore() {
        return this.score;
    }
}