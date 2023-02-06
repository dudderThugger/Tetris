package tetris;

import javax.swing.table.AbstractTableModel;

public class ChoseTetromino {
    public class List extends AbstractTableModel {
        TetrominoList all;
        TetrominoList chosen;


        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public int getColumnCount() {
            return 0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch(columnIndex) {
            }
            return null;
        }
    }
}
