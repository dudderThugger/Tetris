package tetris;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Szelestey Adam
 * @see TetrominoList class, responsible for the storagement of the tetrominos
 */
public class TetrominoList implements Serializable {
    /**
     * The list of the different tetromino's
     */
    ArrayList<Tetromino> all = new ArrayList();
    ArrayList<Tetromino> chosen = new ArrayList();

    /**
     * Adds an element to the tetromino's list
     * @param t the element to be added
     */
    public void add(Tetromino t) {
        this.all.add(t);
    }

    /**
     * @return A random tetromino from the list
     */
    public Tetromino getTetromino() {
        Random random = new Random();
        int upperBound = this.chosen.size();
        int retIdx = random.nextInt(upperBound);
        return (Tetromino)this.chosen.get(retIdx);
    }

    /**
     * Adds the basic tetrominos of the Tetris game
     */
    public void addBasicShapes() {
        all.add(new Tetromino(new int[][]{{1}, {1}, {1}, {1}}, new Color(0, 255, 255), "I"));
        all.add(new Tetromino(new int[][]{{1, 0}, {1, 0}, {1, 1}}, new Color(255, 127, 0), "L"));
        all.add(new Tetromino(new int[][]{{1, 1}, {1, 1}}, new Color(255, 255, 0), "O"));
        all.add(new Tetromino(new int[][]{{0, 1}, {0, 1}, {1, 1}}, new Color(0, 0, 255), "J"));
        all.add(new Tetromino(new int[][]{{0, 1, 1}, {1, 1, 0}}, new Color(0, 255, 0), "Z"));
        all.add(new Tetromino(new int[][]{{0, 1, 0}, {1, 1, 1}}, new Color(128, 0, 128), "T"));
        all.add(new Tetromino(new int[][]{{1, 1, 0}, {0, 1, 1}}, new Color(255, 0, 0), "S"));
        setTetros();
    }

    public int getSize() {
        return all.size();
    }

    public Tetromino getTetrominoAt(int n) {
        return all.get(n);
    }

    public void setTetros() {
        chosen.clear();
        all.forEach((Tetromino tet) -> {
            if(tet.isChosen()) {
                chosen.add(tet);
            }
        });
    }

    ArrayList<Tetromino> getChosen() {
        return chosen;
    }

    public void setChosen(ArrayList<Tetromino> chosen) {
        this.chosen = chosen;
    }
}