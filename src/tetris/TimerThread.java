package tetris;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Szelestey Adam
 * @see TimerThread class, panel and thread to count and display the time since the beginning of the game
 */
public class TimerThread extends JPanel implements Runnable {
    /**
     * Reference of the label displaying the time
     */
    JLabel timeLabel = new JLabel();
    /**
     * Minutes of the time since the beginning
     */
    int mins;
    /**
     * Seconds of the time since the beginning
     */
    int secs;
    /**
     * If the thread is alive
     */
    boolean exit;

    /**
     * Constructor
     */
    public TimerThread() {
        this.setLayout(new FlowLayout(0));
        this.timeLabel.setFont(new Font("joystix monospace", 0, 14));
        this.add(this.timeLabel);
        this.setBounds(1, 10, 140, 100);
        this.mins = 0;
        this.secs = 0;
        this.exit = false;
    }

    /**
     * The run loop of the thread
     */
    public void run() {
        while(true) {
            try {
                if (!this.exit) {
                    this.timeLabel.setText(String.format("TIME %02d:%02d", this.mins, this.secs));
                    Thread.sleep(1000L);
                    if (++this.secs == 60) {
                        ++this.mins;
                        this.secs = 0;
                    }
                    continue;
                }
            } catch (InterruptedException var2) {
                System.out.println("Something went wrong with the timer!");
                var2.printStackTrace();
            }

            return;
        }
    }

    /**
     * Stops the thread, gets called when a game ends or closed
     */
    public void gameOver() {
        this.exit = true;
    }

    /**
     * @return Time spent since the beginning in seconds
     */
    public int getTime() {
        return this.mins * 60 + this.secs;
    }

    /**
     * @param newTime The new time since the beginning in seconds
     */
    public void setTime(int newTime) {
        this.mins = newTime / 60;
        this.secs = newTime - this.mins * 60;
    }
}