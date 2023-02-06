package tetris;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class DrawTetromino extends JPanel {
    GameForm gf;
    JButton[][] buttons;
    JPanel placeholder;
    JTextField namePlace;
    JButton save;
    JButton back;
    int[][] block;
    JTextField rT;
    JTextField gT;
    JTextField bT;

    public DrawTetromino(GameForm gf) {
        this.gf = gf;
        initComponents();
    }

    public void initComponents () {
        this.setSize(490, 600);
        this.setLayout(new BorderLayout());
        placeholder = new JPanel();
        placeholder.setLayout(new GridLayout(5, 5));
        setupButton();
        setUpTF();
        this.setBackground(Color.GRAY);
        this.add(placeholder, BorderLayout.CENTER);
    }

    public void setupButton() {
        buttons = new JButton[5][5];
        block = new int[5][5];
        int width = 20;
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j <5; ++j) {
                int x = i;
                int y = j;
                block[x][y] = 0;
                buttons[x][y] = new JButton(x + ";" + y);
                buttons[x][y].setBackground(Color.WHITE);
                buttons[x][y].setSize(width, width);
                buttons[x][y].addActionListener((e) -> {
                    if(block[x][y] == 0) {
                        block[x][y] = 1;
                        buttons[x][y].setBackground(Color.BLACK);
                    } else {
                        block[x][y] = 0;
                        buttons[x][y].setBackground(Color.WHITE);
                    }
                });
                this.placeholder.add(buttons[x][y]);
            }
        }
    }

    public void setUpTF() {
        JPanel hold = new JPanel();
        JLabel name = new JLabel("NAME: ");
        name.setFont(new Font("joystix monospace", 0, 12));
        JButton back = new JButton("SAVE");
        JLabel rB = new JLabel("R: ");
        rB.setFont(new Font("joystix monospace", 0, 12));
        rT = new JTextField("0",3);
        rT.setFont(new Font("joystix monospace", 0, 12));
        JLabel gB = new JLabel("G: ");
        gB.setFont(new Font("joystix monospace", 0, 12));
        gT = new JTextField("0",3);
        gT.setFont(new Font("joystix monospace", 0, 12));
        JLabel bB = new JLabel("B: ");
        bB.setFont(new Font("joystix monospace", 0, 12));
        bT = new JTextField("0",3);
        bT.setFont(new Font("joystix monospace", 0, 12));
        back.setBackground(Color.RED);
        back.addActionListener((e) -> {
            saveTetromino();
        });
        namePlace = new JTextField(10);
        hold.add(name, BorderLayout.SOUTH);
        hold.add(namePlace);
        hold.add(rB);
        hold.add(rT);
        hold.add(gB);
        hold.add(gT);
        hold.add(bB);
        hold.add(bT);
        hold.add(back);
        this.add(hold, BorderLayout.SOUTH);
    }

    public void saveTetromino(){
        //printBlock();
        cropBlock();
        //printBlock();
        Color c = new Color(Integer.parseInt(rT.getText()), Integer.parseInt(gT.getText()), Integer.parseInt(bT.getText()));
        Tetromino newShape = new Tetromino(block, c, namePlace.getText());
        gf.getTL().add(newShape);
        gf.startMenu();
    }

    public void cropBlock() {
        int startX = 5;
        int endX = 0;
        int startY = 5;
        int endY = 0;
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                if(block[i][j] == 1) {
                    if(i < startX) startX = i;
                    if(j < startY) startY = j;
                    if(i > endX) endX = i;
                    if(j > endY) endY = j;
                }
            }
        }
        int newX = endX - startX + 1;
        int newY = endY - startY + 1;
        if(newX < 0 || newY < 0) {
            return;
        }

        int[][] newBlock = new int[newX][newY];
        for(int i = 0; i < newX; ++i) {
            for(int j = 0; j < newY; ++j) {
                newBlock[i][j] = block[startX + i][startY + j];
            }
        }

        block = newBlock;
    }


    public void printBlock() {
        for(int i = 0; i < block.length; ++i) {
            for(int j = 0; j < block[i].length; ++j) {
                System.out.print(block[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
