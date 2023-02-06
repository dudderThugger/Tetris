package tetris;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * @author Szelestey Adam
 * @see LeaderBoard class, the panel of the leaderboard, controls  the panel and the storagement of the score records
 */
public class LeaderBoard extends JPanel {
    /**
     * Reference of the list that contains the score records
     */
    private ArrayList<ScoreRecord> scores;
    /**
     * Reference of the table that displays the score records
     */
    private JTable table;
    /**
     * Reference of the "BACK" button
     */
    private JButton back;
    /**
     * Reference of the game's frame
     */
    private GameForm gf;

    /**
     * Constructor, sets up the panel and its components
     * @param gf
     */
    public LeaderBoard(GameForm gf) {
        this.gf = gf;
        this.initScore();
        this.setSize(500, 700);
        this.setLayout(new FlowLayout());
        this.table = new JTable(new ScoreTable(this.scores));
        this.table.setFillsViewportHeight(true);
        this.table.setVisible(true);
        this.table.setBackground(Color.LIGHT_GRAY);
        this.table.setFont(new Font("joystix monospace", 0, 10));
        this.table.getTableHeader().setFont(new Font("joystix monospace", 1, 12));
        this.table.getColumnModel().getColumn(2).setPreferredWidth(200);
        this.table.getColumnModel().getColumn(3).setPreferredWidth(100);
        this.add(new JScrollPane(this.table));
        this.back = new JButton("BACK");
        this.back.setFont(new Font("joystix monospace", 0, 12));
        this.back.setBackground(new Color(255, 0, 0));
        this.back.setBounds(400, 600, 80, 50);
        this.back.addActionListener((e) -> {gf.startMenu();});
        this.add(this.back, "South");
    }

    /**
     * Initializes the scores attribute
     */
    public void initScore() {
        if (!this.checkLeaderBoardExists()) {
            this.scores = new ArrayList();
        } else {
            try {
                FileInputStream file = new FileInputStream("save" + File.separator +"leaderboard.txt");
                ObjectInputStream in = new ObjectInputStream(file);
                this.scores = (ArrayList)in.readObject();
                in.close();
            } catch (FileNotFoundException var3) {
                System.out.println("LeaderBoard is missing");
                var3.printStackTrace();
            } catch (IOException var4) {
                var4.printStackTrace();
            } catch (ClassNotFoundException var5) {
                var5.printStackTrace();
            }
        }

    }

    /**
     * Refreshes the scores by calling the initScore method
     */
    public void refreshScore() {
        this.initScore();
    }

    /**
     * Checks whether the leaderboard.txt file exists int save directory
     * @return true if it exists
     */
    public boolean checkLeaderBoardExists() {
        return (new File("save/leaderboard.txt")).exists();
    }

    /**
     * @return Reference of the list that contains the score records
     */
    public ArrayList<ScoreRecord> getTable() {
        return this.scores;
    }

    /**
     * Saves the actual score to the scores list then writes it out into the leaderboard.txt file
     */
    public void gameEnd() {
        try {
            FileOutputStream file = new FileOutputStream("save" + File.separator + "leaderboard.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this.scores);
            out.close();
            file.close();
        } catch (FileNotFoundException var3) {
            var3.printStackTrace();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        this.refreshScore();
    }

    /**
     * @see ScoreTable class, inner class of the LeaderBoard class responsible for the display of the score records
     */
    public class ScoreTable extends AbstractTableModel {
        /**
         * Reference of the scores list
         */
        ArrayList<ScoreRecord> scores;
        /**
         * The names of each column in a score record
         */
        String[] colNames = new String[]{"NO", "NAME", "DATE", "SCORE"};
        /**
         * The classes of each column in a score record
         */
        Class[] classes = new Class[]{String.class, String.class, LocalDateTime.class, Integer.class};

        /**
         * Constructor, sets the scores field
         * @param scores the scores list
         */
        public ScoreTable(ArrayList<ScoreRecord> scores) {
            this.scores = scores;
        }

        /**
         * Override method, returns the class of the column indexed by n
         * @param n  the column being queried
         * @return the class of the column
         */
        public Class<?> getColumnClass(int n) {
            return this.classes[n];
        }

        /**
         * Override method, returns the name of the column indexed by col
         * @param col  the column being queried
         * @return the name of the column
         */
        public String getColumnName(int col) {
            return this.colNames[col];
        }

        /**
         * Override method, returns how many records are in the table
         * @return the number of records
         */
        public int getRowCount() {
            return this.scores.size();
        }

        /**
         * Override method, returns the number of columns in the table
         * @return The number of columns in the table
         */
        public int getColumnCount() {
            return 4;
        }

        /**
         * Override method, returns the element specified by the row- and column index
         * @param rowIndex        the row whose value is to be queried
         * @param columnIndex     the column whose value is to be queried
         * @return  the element specified
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            ScoreRecord selected = (ScoreRecord)this.scores.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return rowIndex + 1 + ".";
                case 1:
                    return selected.getName();
                case 2:
                    return selected.getTime().toString();
                case 3:
                    return selected.getScore();
                default:
                    return new String();
            }
        }
    }
}