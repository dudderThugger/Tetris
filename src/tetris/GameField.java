package tetris;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * @author Szelestey Adam
 * @see GameField class, panel of the game field, responsible for displaying and controling of the game field
 */
public class GameField extends JPanel {
    /**
     * Reference of the game's panel
     */
    private Game game;
    /**
     * The number of rows to display
     */
    private int numberOfRows = 20;
    /**
     * The number of columns to display
     */
    private int numberOfColumns = 10;
    /**
     * The length of a rectangle's side in pixels
     */
    private int sideOfRect;
    /**
     * Reference of the tetromino shapes' list
     */
    private TetrominoList tl;
    /**
     * Reference of the falling tetromino
     */
    private Tetromino tetromino;
    /**
     * Reference of the background's blocks
     */
    private Color[][] background;
    /**
     * Reference of the score object
     */
    private Score score;
    /**
     * Reference of the game's fall thread
     */
    private FallThread fall;

    /**
     * Constructor, sets the panel's attributes and other attributes of the object
     * @param game Reference of the game's panel
     */
    public GameField(Game game) {
        this.setBounds(150, 20, 301, 601);
        this.setBackground(new Color(127, 127, 127));
        this.game = game;
        this.tl = game.getGForm().getTL();
        this.sideOfRect = 30;
        this.score = new Score(this.fall);
        this.background = new Color[this.numberOfRows][this.numberOfColumns];
        this.spawnTetromino();
    }

    /**
     * Starts the game by making a new fall thread and score panel
     */
    public void gameStart() {
        this.fall = new FallThread(this);
        this.fall.start();
        this.score = new Score(this.fall);
    }

    /**
     * Ends the game
     */
    public void gameEnd() {
        this.tetromino = this.tl.getTetromino();
        this.fall.gameOver();
        this.game.gameOver();
    }

    /**
     * Spawns a new tetromino
     */
    public void spawnTetromino() {
        this.tetromino = this.tl.getTetromino();
        this.repaint();
    }

    /**
     * Override method of the JPanel's paintComponent method
     * @param g Reference of the Graphics object of the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.paintField(g);
    }

    /**
     * Draws the background of the game
     * @param g Reference of the Graphics object of the panel
     */
    protected void drawBackground(Graphics g) {
        for(int col = 0; col < this.numberOfColumns; ++col) {
            for(int row = 0; row < this.numberOfRows; ++row) {
                Color color = this.background[row][col];
                if (color != null) {
                    this.drawCell(col, row, color, g);
                }
            }
        }

    }

    /**
     * Draws a tetromino
     * @param g Reference of the Graphics object of the panel
     * @param t Reference of the tetromino
     */
    private void drawTetromino(Graphics g, Tetromino t) {
        for(int i = 0; i < t.getHeight(); ++i) {
            for(int j = 0; j < t.getWidth(); ++j) {
                if (t.getBlock()[i][j] == 1) {
                    this.drawCell(j + t.getX(), i + t.getY(), t.getColor(), g);
                }
            }
        }

    }

    /**
     * Makes the tetromino fall one row, if it can, calls the reachBottom method in other cases
     */
    public void blockFall() {
        if (!this.isBottom()) {
            this.tetromino.step();
        } else {
            this.reachBottom();
        }

        this.repaint();
    }

    /**
     * Builds the tetromino into the background, checks if a row got filled, calls the spawnTetromino method
     */
    public void reachBottom() {
        int[][] old = this.tetromino.getBlock();
        Color c = this.tetromino.getColor();
        int x = this.tetromino.getX();
        int y = this.tetromino.getY();

        for(int i = 0; i < this.tetromino.getHeight(); ++i) {
            for(int j = 0; j < this.tetromino.getWidth(); ++j) {
                if (y < 0) {
                    this.gameEnd();
                    return;
                }

                if (old[i][j] == 1) {
                    this.background[y + i][x + j] = c;
                }
            }
        }

        this.tetromino.reset();
        this.checkFullRow();
        this.spawnTetromino();
    }

    /**
     * Checks whether the tetromino is on the bottom or on another colored block
     * @return true if it is on the bottom or on another colored block
     */
    public boolean isBottom() {
        int[][] block = this.tetromino.getBlock();
        int x = this.tetromino.getX();
        int y = this.tetromino.getY();
        if (this.tetromino.getBottom() == this.numberOfRows) {
            return true;
        } else {
            if (y >= -1) {
                for(int c = 0; c < this.tetromino.getWidth(); ++c) {
                    for(int r = 0; r < this.tetromino.getHeight(); ++r) {
                        if (block[r][c] == 1 && this.background[y + r + 1][x + c] != null) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    /**
     * Checks whether the tetromino is on the left edge or at another bock (on the left)
     * @return true if the tetromino is on the left edge or at another bock (on the left)
     */
    private boolean isLeft() {
        int[][] block = this.tetromino.getBlock();
        int x = this.tetromino.getX();
        int y = this.tetromino.getY();
        if (this.tetromino.getLeftEdge() == 0) {
            return true;
        } else {
            if (y >= 0) {
                for(int c = 0; c < this.tetromino.getWidth(); ++c) {
                    for(int r = 0; r < this.tetromino.getHeight(); ++r) {
                        if (block[r][c] == 1 && this.background[y + r][x + c - 1] != null) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }
    /**
     * Checks whether the tetromino is on the left edge or at another bock (on the left)
     * @return true if the tetromino is on the left edge or at another bock (on the left)
     */
    private boolean isRight() {
        int[][] block = this.tetromino.getBlock();
        int x = this.tetromino.getX();
        int y = this.tetromino.getY();
        if (this.tetromino.getRightEdge() == this.numberOfColumns) {
            return true;
        } else {
            if (y >= 0) {
                for(int c = 0; c < this.tetromino.getWidth(); ++c) {
                    for(int r = 0; r < this.tetromino.getHeight(); ++r) {
                        if (block[r][c] == 1 && this.background[y + r][x + c + 1] != null) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    /**
     * Drops the tetromino
     */
    public void moveBlockDown() {
        while(!this.isBottom()) {
            this.blockFall();
        }
    }

    /**
     * Moves the tetromino one column left if possible
     */
    public void moveBlockLeft() {
        if (!this.isLeft()) {
            this.tetromino.moveLeft();
        }

        this.repaint();
    }

    /**
     * Moves the tetromino one columnt right if possible
     */
    public void moveBlockRight() {
        if (!this.isRight()) {
            this.tetromino.moveRight();
        }

        this.repaint();
    }

    /**
     * Rotates the tetromino
     */
    public void rotateBlock() {
        this.tetromino.rotateBlock();
        if (!this.rotateSuccess()) {
            this.tetromino.rotateBack();
        }

        this.repaint();
    }

    /**
     * Checks whether the rotation was successful
     * @return true if it was successful
     */
    public boolean rotateSuccess() {
        int[][] block = this.tetromino.getBlock();
        int x = this.tetromino.getX();
        int y = this.tetromino.getY();
        if (x + this.tetromino.getWidth() <= this.numberOfColumns && y + this.tetromino.getHeight() <= this.numberOfRows && y >= 0) {
            for(int r = 0; r < this.tetromino.getHeight(); ++r) {
                for(int c = 0; c < this.tetromino.getWidth(); ++c) {
                    if (block[r][c] == 1 && this.background[y + r][x + c] != null) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Paints the cells of the game field
     * @param g The graphics object
     */
    public void paintField(Graphics g) {
        g.setColor(Color.black);

        for(int i = 0; i < this.numberOfColumns; ++i) {
            for(int j = 0; j < this.numberOfRows; ++j) {
                g.drawRect(i * this.sideOfRect, j * this.sideOfRect, this.sideOfRect, this.sideOfRect);
            }
        }

        g.drawRect(0, 0, 300, 600);
        this.drawTetromino(g, this.tetromino);
        this.drawBackground(g);
    }

    /**
     * Checks if there is any full row on the field and deletes them
     */
    public void checkFullRow() {
        int fullRows = 0;
        int first = this.numberOfRows - 1;

        for(int r = 0; r < this.numberOfRows; ++r) {
            boolean full = true;

            for(int c = 0; c < this.numberOfColumns; ++c) {
                if (this.background[r][c] == null) {
                    full = false;
                    break;
                }
            }

            if (full) {
                if (r < first) {
                    first = r;
                }

                ++fullRows;
                this.clearRow(r);
            }

            full = true;
        }

        this.blocksFall(first, fullRows);
        if (fullRows > 0) {
            if (this.checkFullClear()) {
                this.score.scoring(fullRows, true);
            } else {
                this.score.scoring(fullRows, false);
            }
        }

    }

    /**
     * Checks if the field is completely cleared
     * @return true if the field is completely cleared
     */
    private boolean checkFullClear() {
        for(int r = 0; r < this.numberOfRows; ++r) {
            for(int c = 0; c < this.numberOfColumns; ++c) {
                if (this.background[r][c] != null) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Clears a row from the field
     * @param row The index of the row (numbered from top to bottom)
     */
    public void clearRow(int row) {
        for(int c = 0; c < this.numberOfColumns; ++c) {
            this.background[row][c] = null;
        }

    }

    /**
     * Simulates the Tetris's gravity by shifting rows from an index
     * @param first the first row to be shifted
     * @param off the number of rows that are going to be shifted
     */
    public void blocksFall(int first, int off) {
        for(int i = 0; i < off; ++i) {
            for(int r = first + off - 1; r > 0; --r) {
                this.background[r] = this.background[r - 1];
            }

            this.background[0] = new Color[this.numberOfColumns];
        }

    }

    /**
     * @return the score object
     */
    public Score getScore() {
        return this.score;
    }

    /**
     * Draws a cell
     * @param x The x coordinate of the cell (int the 10*20 field)
     * @param y The y coordinate of the cell
     * @param c The color of the cell
     * @param g The graphics object
     */
    public void drawCell(int x, int y, Color c, Graphics g) {
        g.setColor(c);
        g.fillRect(x * this.sideOfRect, y * this.sideOfRect, this.sideOfRect, this.sideOfRect);
        g.setColor(Color.black);
        g.drawRect(x * this.sideOfRect, y * this.sideOfRect, this.sideOfRect, this.sideOfRect);
    }

    /**
     * Resets the field to fully cleared
     */
    public void resetField() {
        this.background = new Color[this.numberOfRows][this.numberOfColumns];
        this.score.resetScore(this.fall);
        this.spawnTetromino();
    }

    /**
     * @return The background's color box
     */
    public Color[][] getBackgroundBlocks() {
        return this.background;
    }

    /**
     * @return The game's thread's fall speed/ period
     */
    public long getFallSpeed() {
        return this.fall.getFallSpeed();
    }

    /**
     * Loads a game from a savable game object
     * @param savable the savable game object, which stores the stage of the game
     */
    public void loadGame(SavableGame savable) {
        this.background = savable.getBackgroundBlocks();
        this.fall.setFallSpeed(savable.getFallSpeed());
    }

    TetrominoList getTL() {
        return tl;
    }
}