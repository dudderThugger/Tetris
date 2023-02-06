package tetris;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Szelestey Adam
 * @see SavableGame class, this class contains the datas of a game's state, it is used to serialize a game
 */
public class SavableGame implements Serializable {
    /**
     * The background blocks of the game
     */
    private Color[][] background;
    /**
     * For how long the game was going
     */
    private int time;
    /**
     * The score of the game when it was closed
     */
    private int score;
    /**
     * The level of the game closed
     */
    private int level;
    /**
     * The name of the player that started the game
     */
    private String name;
    /**
     * The period of the falling of the pieces
     */
    private long fallSpeed;
    /**
     * The amount of cleared lines the player collected
     */
    private int clearedLines;

    private ArrayList<Tetromino> tetros;

    /**
     * Constructor, initializes the object's field
     * @param time
     * @param score
     * @param clearedLines
     * @param level
     * @param name
     * @param bg
     * @param fs
     */
    public SavableGame(int time, int score, int clearedLines, int level, String name, Color[][] bg, long fs, ArrayList<Tetromino> tetros) {
        this.time = time;
        this.score = score;
        this.clearedLines = clearedLines;
        this.level = level;
        this.name = name;
        this.background = bg;
        this.fallSpeed = fs;
        this.tetros = tetros;
    }

    /**
     * @return The name of the player who started the game
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The score of the game closed
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @return For how long the game was going in secs
     */
    public int getTime() {
        return this.time;
    }

    /**
     * @return The background's blocks
     */
    public Color[][] getBackgroundBlocks() {
        return this.background;
    }

    /**
     * @return The period of the game's fall thread
     */
    public long getFallSpeed() {
        return this.fallSpeed;
    }

    /**
     * @return The level field of the game closed
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * @return The cleared lines field of the game closed
     */
    public int getClearedLines() {
        return this.clearedLines;
    }

    ArrayList<Tetromino> getTetros() {return tetros;}
}