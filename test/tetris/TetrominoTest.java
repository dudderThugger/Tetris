package tetris;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TetrominoTest {
    Tetromino tetromino;

    @Before
    public void init() {
        TetrominoList tl = new TetrominoList();
        tl.addBasicShapes();
        tetromino = tl.getTetromino();
    }

    @Test
    public void testStep() {
        int yBefore = tetromino.getY();
        tetromino.step();
        int yAfter = tetromino.getY();
        assertEquals("tetromino y coordinate before and after stepping", yBefore + 1, yAfter);
    }

    @Test
    public void testMoveLeft() {
        int xBefore = tetromino.getX();
        tetromino.moveLeft();
        int xAfter = tetromino.getX();
        assertEquals("tetromino x coordinate before and after moving left", xBefore - 1, xAfter);
    }

    @Test
    public void testMoveRight() {
        int xBefore = tetromino.getX();
        tetromino.moveRight();
        int xAfter = tetromino.getX();
        assertEquals("tetromino x coordinate before and after moving right", xBefore + 1, xAfter);
    }

    @Test
    public void testRotation() {
        int[][] beforeRotate = tetromino.getBlock();
        tetromino.rotateBlock();
        int[][] afterRotate = tetromino.getBlock();
        assertNotEquals("tetromino's blocks change after rotation", beforeRotate, afterRotate);
    }
}
