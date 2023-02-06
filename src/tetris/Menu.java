package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Szelestey Adam
 * @see Menu class, this class is responsible for the building of the menu panel and its components
 */
public class Menu extends JPanel {
    /**
     * Reference of the game form frame
     */
    private GameForm gf;
    /**
     * Reference of the "TETRIS BY DUDDER" label
     */
    private JLabel tetrisByDudder;
    /**
     * Reference of the "NEW GAME" button
     */
    private JButton newGame;
    /**
     * Reference of the "LEADER BOARD" button
     */
    private JButton leaderBoard;
    /**
     * Reference of the "QUIT" button
     */
    private JButton quit;
    /**
     * Reference of the "LOAD GAME" button
     */
    private JButton loadGame;

    private JButton drawTetromino;

    /**
     * Constructor, sets the panel and its components
     * @param gf Reference of the game form frame
     */
    public Menu(GameForm gf) {
        this.gf = gf;
        this.setSize(500, 700);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        int buttonwidth = 465;
        int buttonheight = 100;
        int fontSize = 30;
        this.newGame = new JButton("New Game");
        this.tetrisByDudder = new JLabel("TETRIS BY DUDDER");
        this.loadGame = new JButton("LOAD GAME");
        drawTetromino = new JButton("DRAW TETROMINO");
        drawTetromino.addActionListener(new DrawTetrominoAction());
        this.leaderBoard = new JButton("Leaderboard");
        this.quit = new JButton("Quit");
        JPanel chaos = new JPanel();
        GridLayout glo = new GridLayout(6, 0, 20, 20);
        chaos.setLayout(glo);
        chaos.setBackground(Color.lightGray);
        chaos.add(this.tetrisByDudder);
        chaos.add(this.newGame);
        chaos.add(this.loadGame);
        chaos.add(drawTetromino);
        chaos.add(this.leaderBoard);
        chaos.add(this.quit);
        this.tetrisByDudder.setForeground(Color.BLACK);
        //this.tetrisByDudder.setBounds(10, 10, buttonwidth, buttonheight);
        this.tetrisByDudder.setFont(new Font("joystix monospace", 0, 27));
        this.newGame.setBackground(new Color(0, 255, 255));
        //this.newGame.setBounds(10, 120, buttonwidth, buttonheight);
        this.newGame.setFont(new Font("joystix monospace", 0, fontSize));
        this.newGame.addActionListener(new NewGameAction());
        this.loadGame.setBackground(new Color(0, 255, 0));
        //this.loadGame.setBounds(10, 230, buttonwidth, buttonheight);
        this.loadGame.setFont(new Font("joystix monospace", 0, fontSize));
        this.loadGame.addActionListener(new LoadGameAction());
        this.drawTetromino.setBackground(Color.PINK);
        //this.tetrisByDudder.setBounds(10, 10, buttonwidth, buttonheight);
        this.drawTetromino.setFont(new Font("joystix monospace", 0, 27));
        this.leaderBoard.setBackground(new Color(255, 255, 0));
        //this.leaderBoard.setBounds(10, 340, buttonwidth, buttonheight);
        this.leaderBoard.setFont(new Font("joystix monospace", 0, fontSize));
        this.leaderBoard.addActionListener(new LeaderBoardAction());
        this.quit.setBackground(new Color(255, 0, 0));
        //this.quit.setBounds(10, 450, buttonwidth, buttonheight);
        this.quit.setFont(new Font("joystix monospace", 0, fontSize));
        this.quit.addActionListener(new QuitAction());
        this.add(chaos);
        chaos.setBounds(25, 0, 440, 600);
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(0, new File("joystix monospace.ttf")));
        } catch (FontFormatException | IOException var6) {
            var6.printStackTrace();
        }

    }

    /**
     * @see NewGameAction class, inner class of the Menu, the "NEW GAME" button's action listener
     */
    class NewGameAction implements ActionListener {
        /**
         * Shows the game field panel and hides other panels
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {
            gf.game();
        }
    }

    /**
     * @see QuitAction class, inner class of the Menu, the "QUIT" button's action listener
     */
    class QuitAction implements ActionListener {

        /**
         * Disposes the frame
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {
            gf.exitGame();
        }
    }

    /**
     * @see LeaderBoardAction class, inner class of the Menu, the "LEADERBOARD" button's action listener
     */
    class LeaderBoardAction implements ActionListener {
        /**
         * Shows the leaderboard and hides other panels
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {
            gf.leaderBoard();
        }
    }

    /**
     * @see LoadGameAction class, inner class of the Menu, the "LOAD GAME" button's action listener
     */
    class LoadGameAction implements ActionListener {
        /**
         * Shows the last state of the game, when it was closed
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {
            if ((new File("save/game.txt")).exists()) {
                gf.continueGame();
            }

        }
    }

    class DrawTetrominoAction implements ActionListener {
        /**
         * Shows the last state of the game, when it was closed
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {
            gf.drawTetromino();
        }
    }
}