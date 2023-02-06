package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Szelestey Adam
 * @see Game class, this panel is responsible for the building and controling of the game panel and its compnents
 */
public class Game extends JPanel {
    /**
     * Reference of the game's game field object
     */
    private GameField gameField;
    /**
     * Reference of the list of the score records
     */
    private ArrayList<ScoreRecord> scores;
    /**
     * Reference of the game's timer thread object as a panel
     */
    private TimerThread timer;
    /**
     * Reference of the game's timer thread object as a thread
     */
    private Thread timerThread;
    /**
     * Reference of the game's score object
     */
    private Score score;
    /**
     * Reference of the user's name
     */
    private String name;
    /**
     * Reference of the placeholder panel for requesting name
     */
    private JPanel holder;
    /**
     * Reference of the "ADD NAME:" label
     */
    private JLabel addName;
    /**
     * Reference of the text field for the user's name
     */
    private JTextField namePlace;
    /**
     * Reference of the game's frame
     */
    private GameForm gf;
    /**
     * Reference of the "GAME OVER!" button
     */
    private JButton gameOver;
    /**
     * Boolean which means if a game has already ended since starting the application
     */
    private boolean gameEnded;

    /**
     * Constructor, sets the panel
     * @param gameForm Reference of the game's frame
     */
    public Game(GameForm gameForm) {
        this.setSize(500, 700);
        this.setLayout((LayoutManager)null);
        this.setFocusable(true);
        this.scores = gameForm.getLeaderBoard().getTable();
        this.gf = gameForm;
    }

    public void chooseTetrominos() {

    }
    /**
     * Requests the username by making a new panel in the holder object
     */
    public void requestName() {
        this.holder = new JPanel();
        if (this.gameField != null) {
            this.gameField.setVisible(false);
            this.timer.setVisible(false);
            this.score.setVisible(false);
            this.remove(this.gameOver);
        }

        this.holder.setBackground(Color.GRAY);
        this.holder.setSize(500, 700);
        this.holder.setLayout((LayoutManager)null);
        this.addName = new JLabel("Add a name:");
        this.addName.setFont(new Font("joystix monospace", 0, 20));
        this.addName.setBounds(30, 100, 400, 100);
        this.namePlace = new JTextField();
        this.namePlace.setBackground(Color.LIGHT_GRAY);
        this.namePlace.setFont(new Font("joystix monospace", 0, 20));
        this.namePlace.setBounds(30, 210, 400, 40);
        this.namePlace.addActionListener(new NameAction());
        this.holder.add(this.addName);
        this.holder.add(this.namePlace);
        this.namePlace.setEditable(true);
        this.add(this.holder);
    }

    public void chooseTetros() {
        if (this.holder != null) {
            this.holder.setVisible(false);
        }
        holder = new JPanel();
        holder.setLayout(new BorderLayout());
        holder.add(new ChooseTetrominos(this), BorderLayout.CENTER);
        this.add(holder);
        holder.setBounds(0, 0, 500, 600);
    }

    /**
     * Makes a new game field or resets it if it was already created, makes a new timer thread
     * sets other components of the game too
     */
    public void gameStart() {
        this.gameEnded = false;
        this.saveOnExit();
        if (this.gameField == null) {
            this.addKeyListener(new KeyActions());
            this.gameField = new GameField(this);
        }

        this.gameField.resetField();
        this.gameField.gameStart();
        this.gameField.setVisible(true);
        if (this.holder != null) {
            this.holder.setVisible(false);
        }

        this.timer = new TimerThread();
        this.score = this.gameField.getScore();
        this.timerThread = new Thread(this.timer);
        this.timerThread.start();
        this.add(this.gameField);
        this.score = this.gameField.getScore();
        this.add(this.score);
        this.add(this.timer);
    }

    /**
     * Hides the game field adds the score to the leaderboard stops the timer thread, sets the "GAME OVER!" button
     */
    public void gameOver() {
        this.gameField.setVisible(false);
        File delete = new File("save/game.txt");
        delete.delete();
        this.saveOnExit();
        this.gameEnded = true;
        this.scores.add(new ScoreRecord(this.name, this.score.getScore(), LocalDateTime.now()));
        Collections.sort(this.scores, (o2, o1) -> {
            return o1.getScore() - o2.getScore();
        });
        this.timer.gameOver();
        this.gameOver = new JButton("GAME OVER!!!!");
        this.gameOver.setFont(new Font("joystix monospace", 1, 20));
        this.gameOver.setBackground(Color.red);
        this.gameOver.addActionListener((e) -> {
            this.gf.gameEnd();
        });
        this.add(this.gameOver);
        this.gameOver.setBounds(100, 200, 300, 200);
    }

    /**
     * Saves the state of the game to a SavableGame object, gets called on exiting
     */
    public void saveOnExit() {
        if (this.gameField != null && this.gameField.isVisible()) {
            try {
                FileOutputStream file = new FileOutputStream("save/game.txt");
                ObjectOutputStream out = new ObjectOutputStream(file);
                SavableGame save = new SavableGame(this.timer.getTime(), this.score.getScore(), this.score.getClearedLines(),
                        this.score.getLevel(), this.name, this.gameField.getBackgroundBlocks(), this.gameField.getFallSpeed(),
                        this.gf.getTL().getChosen());
                out.writeObject(save);
                out.close();
                file.close();
                gf.saveTetros();
            } catch (FileNotFoundException var4) {
                var4.printStackTrace();
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        }

    }

    /**
     * Continues a game stored in a SavableGame object
     * @param savableGame the state of the game, which will be continued
     */
    public void continueGame(SavableGame savableGame) {
        if (this.gameField != null && !this.gameEnded) {
            this.gameField.setVisible(true);
        } else {
            this.gameStart();
        }

        this.name = savableGame.getName();
        this.timer.setTime(savableGame.getTime());
        this.gameField.loadGame(savableGame);
        this.score.setScoreLevel(savableGame.getScore(), savableGame.getLevel(), savableGame.getClearedLines());
        this.score.updateScoreLabel();
        this.score.updateLevelLabel();
    }

    /**
     * @see KeyActions class, inner class of the Game, responsible for the key events of the game panel
     */
    public class KeyActions extends KeyAdapter {
        /**
         * Moves the object to the specified direction
         * @param e the event to be processed
         */
        public void keyTyped(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 'a':
                    Game.this.gameField.moveBlockLeft();
                    break;
                case 'd':
                    Game.this.gameField.moveBlockRight();
                    break;
                case 's':
                    Game.this.gameField.moveBlockDown();
                    break;
                case 'w':
                    Game.this.gameField.rotateBlock();
            }

        }
    }

    /**
     * @see NameAction class, inner class of the Game, the event listener of the name text field
     */
    public class NameAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Game.this.name = Game.this.namePlace.getText();
            chooseTetros();
        }
    }

    GameField getGF() {
        return gameField;
    }

    GameForm getGForm() {
        return gf;
    }
}