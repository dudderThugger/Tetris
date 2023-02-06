package tetris;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Szelestey Adam
 * @see Score class, panel responsible for counting and displaying of the score and level
 */
public class Score extends JPanel {
    /**
     * The game's actual level
     */
    private int level = 1;
    /**
     * The game's actual score
     */
    private int score = 0;
    /**
     * Cleared lines since the game started
     */
    private int clearedLines = 0;
    /**
     * Reference of the game's fall thread
     */
    private FallThread fall;
    /**
     *Label for the actual score
     */
    private JLabel scoreLabel;
    /**
     * Label for the actual level
     */
    private JLabel levelLabel;

    /**
     * Constructor, initializes the panel and its components
     * @param fall The game's thread
     */
    public Score(FallThread fall) {
        Font arcade = new Font("joystix monospace", 0, 12);
        this.setBounds(1, 575, 140, 100);
        this.fall = fall;
        this.setLayout(new FlowLayout(0));
        this.scoreLabel = new JLabel("SCORE   " + this.score);
        this.scoreLabel.setFont(arcade);
        this.levelLabel = new JLabel("LEVEL   " + this.level);
        this.levelLabel.setFont(arcade);
        this.add(this.scoreLabel);
        this.add(this.levelLabel);
        this.setVisible(true);
    }

    /**
     * Calculates the score to be added to the object's score field then add it to it, gets called when a row got filled
     * @param fullRows The rows that the player filled at the moment
     * @param fullclear Whether the board got fully cleared or not
     */
    public void scoring(int fullRows, boolean fullclear) {
        int score = 1;
        if (fullclear) {
            score *= 10;
        }

        switch (fullRows) {
            case 1:
                score = score * 100 * this.level;
                break;
            case 2:
                score = score * 300 * this.level;
                break;
            case 3:
                score = score * 500 * this.level;
                break;
            case 4:
                score = score * 800 * this.level;
        }

        this.score += score;
        this.updateScoreLabel();
        this.clearedLines += fullRows;
        if (this.clearedLines >= this.level * 10 || this.clearedLines == 150) {
            this.clearedLines = 0;
            ++this.level;
            this.fall.lvlUp();
            this.updateLevelLabel();
        }

    }

    /**
     * Updates the game's score label
     */
    public void updateScoreLabel() {
        this.scoreLabel.setText("SCORE: " + this.score);
    }

    /**
     * Updates the game's level label
     */
    public void updateLevelLabel() {
        this.levelLabel.setText("LEVEL: " + this.level);
    }

    /**
     * @return The actual score of the game
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @return The actual level of the game
     */
    public int getLevel() {
        return this.level;
    }

    /**
     *
     * @return The cleared lines since the start of the game
     */
    public int getClearedLines() {
        return this.clearedLines;
    }

    /**
     * Sets the score to an actual state, gets called when a game is loaded
     * @param score The score of the new state
     * @param level The level of the new state
     * @param clearedLines The cleared lines of the new state
     */
    public void setScoreLevel(int score, int level, int clearedLines) {
        this.score = score;
        this.level = level;
        this.clearedLines = clearedLines;
    }

    /**
     * Resets the score to the starting position, and sets the new value of the fall field
     * @param fall The new value of the fall field
     */
    public void resetScore(FallThread fall) {
        this.fall = fall;
        this.score = 0;
        this.level = 1;
        this.clearedLines = 0;
    }
}