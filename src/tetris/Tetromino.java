package tetris;

import java.awt.Color;
import java.io.Serializable;

/**
 * @author Szelestey Adam
 * @see Tetromino class, responsible for the controling of a tetromino
 */
public class Tetromino implements Serializable {
    /**
     * The x coordinate of the tetromino's top left corner
     */
    private int x;
    /**
     * The y coordinate of the tetromino's top left corner
     */
    private int y;
    /**
     * The blocks of the tetromino 1 means if there is a block at that place 0 if there is not
     */
    private int[][] block;
    /**
     * The color of the tetromino
     */
    private Color color;
    /**
     * The state of the tetromino's rotation, there is always 4 state
     */
    private int rotation;
    /**
     * Name of the tetromino
     */
    String name;
    /**
     * The different shapes of each rotation
     */
    int[][][] shapes;

    boolean chosen;

    /**
     * Constructor
     * @param block The starting state of the tetromino's blocks
     * @param color The color of the tetromino
     * @param name The name of the tetromino
     */
    public Tetromino(int[][] block, Color color, String name) {
        this.block = block;
        this.color = color;
        this.name = name;
        this.init();
        this.rotation = 0;
        int[][] var10000 = this.shapes[this.rotation];
        this.x = (10 - this.getWidth()) / 2;
        this.y = -this.getHeight();
        chosen = true;
    }

    /**
     * Initializes the state of the tetromino's rotations
     */
    private void init() {
        this.shapes = new int[4][][];

        for(int i = 0; i < 4; ++i) {
            int rows = this.block[0].length;
            int cols = this.block.length;
            this.shapes[i] = new int[rows][cols];

            for(int y = 0; y < rows; ++y) {
                for(int x = 0; x < cols; ++x) {
                    this.shapes[i][y][x] = this.block[cols - x - 1][y];
                }
            }

            this.block = this.shapes[i];
        }

    }

    /**
     * @return The state of the blocks
     */
    public int[][] getBlock() {
        return this.block;
    }

    /**
     * @return The color of the tetromino
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @return The height of the tetromino's actual state
     */
    public int getHeight() {
        return this.block.length;
    }

    /**
     * @return The width of the tetromino's actual state
     */
    public int getWidth() {
        return this.block[0].length;
    }

    /**
     * @return The x coordinate of the tetromino's top left corner (as the field's cell)
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return They coordinate of the tetromino's top left corner (as the field's cell)
     */
    public int getY() {
        return this.y;
    }

    /**
     * Moves the tetromino one column left
     */
    public void moveLeft() {
        --this.x;
    }

    /**
     * Moves the tetroimno one column right
     */
    public void moveRight() {
        ++this.x;
    }

    /**
     * Moves the tetromino one row down
     */
    public void step() {
        if (this.y < 20 - this.getHeight()) {
            ++this.y;
        }

    }

    /**
     * Checks if the tetromino is at the bottom of the field
     * @return True if it is at the bottom
     */
    public boolean isEdge() {
        return this.y == 20 - this.getHeight();
    }

    /**
     * Changes the tetromino's rotation
     */
    public void rotateBlock() {
        ++this.rotation;
        if (this.rotation == 4) {
            this.rotation = 0;
        }

        this.block = this.shapes[this.rotation];
    }

    /**
     * @return The y coordinate of the tetromino's bottom
     */
    public int getBottom() {
        return this.y + this.getHeight();
    }

    /**
     * @return The x coordinate of the tetromino's left side
     */
    public int getLeftEdge() {
        return this.x;
    }

    /**
     * @return The x coordinate of the tetromino's right side
     */
    public int getRightEdge() {
        return this.x + this.getWidth();
    }

    /**
     * @return The name of the tetromino
     */
    public String getName() {
        return this.name;
    }

    /**
     * Moves the tetromino to its starting place
     */
    public void reset() {
        this.x = (10 - this.getWidth()) / 2;
        this.y = -this.getHeight();
        this.rotation = 0;
    }

    /**
     * Rotates the tetromino one rotation back
     */
    public void rotateBack() {
        --this.rotation;
        if (this.rotation == -1) {
            this.rotation = 3;
        }

        this.block = this.shapes[this.rotation];
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean c) {
        chosen = c;
    }
}