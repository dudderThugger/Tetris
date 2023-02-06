package tetris;

/**
 * @author Szelestey Adam
 * @see FallThread class, this thread calls the game field's blockFall method periodicly
 */
public class FallThread extends Thread {
    /**
     * Reference of the game field
     */
    private GameField gf;
    /**
     * The period
     */
    private long fallSpeed;
    /**
     * True means the thread is alive
     */
    private boolean exit;

    /**
     * Constructor, sets the references and starting values
     * @param gf
     */
    public FallThread(GameField gf) {
        this.gf = gf;
        this.fallSpeed = 500L;
        this.exit = false;
    }

    /**
     * Shortens the period by 20%, gets called when leveling up
     */
    public void lvlUp() {
        this.fallSpeed = Math.round((double)this.fallSpeed * 0.8);
    }

    /**
     * The threads run loop
     */
    public void run() {
        while(!this.exit) {
            try {
                sleep(this.fallSpeed);
                this.gf.blockFall();
            } catch (InterruptedException var2) {
                System.out.println(var2);
            }
        }

    }

    /**
     * Stops the thread, gets called when the game of the thread ends
     */
    public void gameOver() {
        this.exit = true;
    }

    /**
     * Sets the period, gets called when you load a game
     * @param fallSpeed New period
     */
    public void setFallSpeed(long fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    /**
     * @return The period
     */
    public long getFallSpeed() {
        return this.fallSpeed;
    }
}