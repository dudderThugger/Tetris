package tetris;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class ChooseTetrominos extends JPanel {
    Game game;

    public ChooseTetrominos(Game game) {
        this.game = game;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GRAY);

        JPanel center = new JPanel();
        center.setBackground(Color.lightGray);
        JTable table = new JTable(new ChooseTable(game.getGForm().getTL()));
        table.getTableHeader().setFont(new Font("joystix monospace", 1, 20));
        table.setFont(new Font("joystix monospace", 1, 20));
        table.setBackground(Color.LIGHT_GRAY);
        center.add(new JScrollPane(table));

        JPanel top = new JPanel();
        JLabel text = new JLabel("CHOOSE THE TETROMINOS");
        text.setFont(new Font("joystix monospace", 1, 20));
        top.setBackground(Color.lightGray);
        top.add(text);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.lightGray);
        JButton start = new JButton("START GAME");
        start.addActionListener((e) -> {
            game.getGForm().getTL().setTetros();
            game.gameStart();
        });
        start.setFont(new Font("joystix monospace", 1, 12));
        start.setBackground(Color.GREEN);
        bottom.add(start);

        this.add(bottom,BorderLayout.SOUTH);
        this.add(center, BorderLayout.CENTER);
        this.add(top, BorderLayout.NORTH);
    }

    public class ChooseTable extends AbstractTableModel {
        TetrominoList all;

        @Override
        public int getRowCount() {
            return all.getSize();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Tetromino tetrominoAt = all.getTetrominoAt(rowIndex);

            switch(columnIndex) {
                case 0: return tetrominoAt.getName();
                case 1: return tetrominoAt.isChosen();
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if(columnIndex == 1) return true;
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if(columnIndex == 1) {
                Tetromino at = all.getTetrominoAt(rowIndex);
                at.setChosen((boolean) aValue);
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class[] classes = {String.class, Boolean.class};
            return classes[columnIndex];
        }

        @Override
        public String getColumnName(int col) {
            super.getColumnName(col);
            String[] header = {"NAME", "CHOSEN"};
            return header[col];
        }

        public ChooseTable(TetrominoList all) {
            this.all = all;
            setFont(new Font("joystix monospace", 0, 10));
        }
    }
}
