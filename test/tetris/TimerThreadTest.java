package tetris;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimerThreadTest {
    TimerThread tt;

    @Before
    public void init() {
        tt = new TimerThread();
    }

    @Test
    public void testTimerGetTime() {
        int atStart = tt.getTime();
        assertEquals("time should be zero at start", 0, atStart);
    }

    @Test
    public void testTimerSetTime() {
        tt.setTime(25);
        int secsAfter = 25;
        int realTime = tt.getTime();
        assertEquals("time is set to 25", secsAfter, realTime);
    }

    @Test
    public void testRunLoop() throws InterruptedException {
        Thread timer = new Thread(tt);
        timer.start();
        Thread.sleep(4500);
        tt.gameOver();
        int timerTime = tt.getTime();
        int expected = 4;
        assertEquals("after 4500 miliseconds the time should be 4", expected, timerTime);
    }
}
