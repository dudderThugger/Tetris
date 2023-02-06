package tetris;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Szelestey Adam
 * @see GameForm class, the frame of the game
 */
public class GameForm extends JFrame {
    /**
     * Reference of the menu panel
     */
    private Menu menu;
    /**
     * Reference of the game panel
     */
    private Game game;
    /**
     * Reference of the leaderboard panel
     */
    private LeaderBoard leaderBoard;

    /**
     * Constructor, calls the init method
     */
    public GameForm() {
        this.initComponents();
    }

    private JPanel draw;

    TetrominoList all;

    /**
     * Sets the frame and initializes the three main panels of the game
     */
    private void initComponents() {
        this.menu = new Menu(this);
        this.leaderBoard = new LeaderBoard(this);
        this.draw = new DrawTetromino(this);
        this.game = new Game(this);
        this.setDefaultCloseOperation(3);
        this.setSize(500, 700);
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Tetris");
        this.getContentPane().add(this.menu);
        this.getContentPane().add(this.game);
        this.getContentPane().add(draw);
        this.getContentPane().add(this.leaderBoard);
        this.setLocationRelativeTo((Component)null);
        setUpAll();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                GameForm.this.game.saveOnExit();
                e.getWindow().dispose();
            }});
        this.startMenu();
    }

    public void setUpAll() {
        if( (new File("save/tetros.txt")).exists()) {
            try {
                FileInputStream file = new FileInputStream("save" + File.separator +"tetros.txt");
                ObjectInputStream in = new ObjectInputStream(file);
                this.all = (TetrominoList) in.readObject();
                in.close();
            } catch (FileNotFoundException var3) {
                var3.printStackTrace();
            } catch (IOException var4) {
                var4.printStackTrace();
            } catch (ClassNotFoundException var5) {
                var5.printStackTrace();
            }
        } else {
            all = new TetrominoList();
            all.addBasicShapes();
        }
    }

    public void saveTetros() {
        try {
            FileOutputStream file = new FileOutputStream("save/tetros.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(all);
            out.close();
            file.close();
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    public void exitGame() {
        saveTetros();
        this.dispose();
    }

    /**
     * Shows the menu and hides other panels
     */
    public void startMenu() {
        this.menu.setVisible(true);
        this.game.setVisible(false);
        draw.setVisible(false);
        this.leaderBoard.setVisible(false);
    }

    /**
     * Shows the game panel and hides other panels
     */
    public void game() {
        this.game.requestName();
        this.menu.setVisible(false);
        this.leaderBoard.setVisible(false);
        draw.setVisible(false);
        this.game.setVisible(true);
        this.game.requestFocusInWindow();
    }

    /**
     * Shows the leaderboard and hides other panels
     */
    public void leaderBoard() {
        this.menu.setVisible(false);
        this.game.setVisible(false);
        draw.setVisible(false);
        this.leaderBoard.setVisible(true);
    }

    /**
     * Shows the menu and calls the leaderboard's gameEnd method
     */
    public void gameEnd() {
        this.game.setVisible(false);
        this.menu.setVisible(true);
        this.leaderBoard.setVisible(false);
        this.leaderBoard.gameEnd();
    }

    /**
     * The main method of the application, makes a new frame and sets it visible
     * @param args
     */
    public static void main(String[] args) {
        GameForm game = new GameForm();
        game.setVisible(true);
    }

    /**
     * @return the leaderboard
     */
    public LeaderBoard getLeaderBoard() {
        return this.leaderBoard;
    }

    /**
     * If the save/game.txt files contains a savable game's serialization loads the game from that state,
     * in other cases nothing happens
     */
    public void continueGame() {
        try {
            if ((new File("save/game.txt")).exists()) {
                FileInputStream file = new FileInputStream("save" + File.separator + "game.txt");
                ObjectInputStream in = new ObjectInputStream(file);
                SavableGame sg = (SavableGame)in.readObject();
                this.getTL().setChosen(sg.getTetros());
                this.game.continueGame(sg);
                in.close();
            }
        } catch (IOException var3) {
            var3.printStackTrace();
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
        }

        this.game.setVisible(true);
        this.menu.setVisible(false);
        draw.setVisible(false);
        this.leaderBoard.setVisible(false);
        this.game.requestFocusInWindow();
    }

    public void drawTetromino() {
        this.menu.setVisible(false);
        draw.setVisible(true);
    }

    TetrominoList getTL() {
        return all;
    }
}
