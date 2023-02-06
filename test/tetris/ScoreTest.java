package tetris;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {
    Score score;

    @Before
    public void init() {
        GameForm gameForm = new GameForm();
        Game game = new Game(gameForm);
        GameField gf = new GameField(game);
        score = new Score(new FallThread(gf));
    }

    @Test
    public void testScoreStart() {
        int start = score.getScore();
        int expect = 0;
        assertEquals("0 score on start", expect, start);
    }

    @Test
    public void testScoring() {
        int before = this.score.getScore();
        score.scoring(1, false);
        int oneRowAtLvlZero = 100;
        int after = score.getScore();
        assertEquals("the score should grow by 100 after clearing a row", before + oneRowAtLvlZero, after);
    }

    @Test
    public void testSettingScore() {
        int newScore = 20000;
        int newLevel = 3;
        int newClearedLines = 33;
        score.setScoreLevel(newScore, newLevel, newClearedLines);
        int after = score.getScore();
        assertEquals("score should be set at 20000", newScore, after);
    }
}
