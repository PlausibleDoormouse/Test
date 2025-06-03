//package minesweeper;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MinesweeperGame extends IOException{
    
    private class MineTile extends JButton {
        int r;
        int c;
        boolean flag;

        public MineTile(int r, int c){

            this.r = r;
            this.c = c;
            this.flag = false;
        }
    }

    int tileSize = 40;
    int width = 700;
    int height = 500;
    int numrows = 8;
    int colums = 8;

    JFrame frame = new JFrame("Start Game");
    JLabel textlabel = new JLabel();
    JPanel textpanel = new JPanel();
    JPanel boardPanel = new JPanel();

    //ImageIcon image;
    
    int minecount = 10;

    MineTile[][] board = new MineTile[8][8];
    ArrayList<MineTile> mineList; 

    Random random = new Random();

    int tilesClicked = 0; //Goal to click all Tiles

    boolean GameOver = false;

    /*public void start(String[] args) throws IOException, InterruptedException {
        Scanner Round1 = new Scanner(System.in); // create Scanner
        new ProcessBuilder("cmd", "/c", "pause").inheritIO().start().waitFor();
        //System.in.read();
        String Start = Round1.nextLine(); // read input
        JLabel startlabel = new JLabel();
        startlabel.setFont(new Font("Arial", Font.BOLD, 25) );
        startlabel.setText("Start");
        textlabel.add(startlabel, Start);

        boolean gameOver = false;
        if (gameOver) {
            System.out.println("Hello, World!");
        } else {
            System.out.println("Error 404");
            throw new IOException("hello there");
        }
    }*/
    
   
    MinesweeperGame() { // Der Konstruktor übergibt Werte wie ein Inventar an andere Methoden
        //frame.setVisible(true); erst laden nachdem alle anderen Komponenten geladen wurden
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());               
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        textlabel.setFont(new Font("Arial", Font.BOLD, 25) );
        textlabel.setHorizontalAlignment(JLabel.CENTER);
        textlabel.setText("Minesweeper: " + Integer.toString(minecount) );
        textlabel.setOpaque(true);

        textpanel.setLayout(new BorderLayout());
        textpanel.add(textlabel);
        frame.add(textpanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(8, 8));             
        boardPanel.setBackground(Color.BLACK);
        frame.add(boardPanel);
        
        for (int r = 0; r < numrows; r++) {
            for(int c = 0; c < colums; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                //tile.setText("😁");
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(GameOver){
                            return;
                        }
                        MineTile tile = (MineTile) e.getSource();

                        if (e.getButton() == MouseEvent.BUTTON1) { //left
                            if (tile.flag == false){
                                if (mineList.contains(tile)) {
                                    try {
                                        revealMines();
                                    } catch (IOException ex) {
                                    }
                                }
                                else {
                                    checkMine(tile.r, tile.c);
                                    tile.setIcon(null);
                                }
                            }
                        }
                        
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            if ( tile.flag == false && tile.isEnabled()) {
                                try{
                                    File fila = new File("C:\\Users\\lgeorg\\Desktop\\Minesweeper\\src\\flag.png");
                                    BufferedImage bufferedImaga = ImageIO.read(fila);
                                    ImageIcon imageIca = new ImageIcon(bufferedImaga);
                                    tile.setIcon(imageIca);
                                    tile.flag = true;
                                } catch (IOException ex) {
                                }
                            }
                            else if(tile.flag == true && tile.isEnabled()) {
                                tile.setIcon(null);
                                tile.flag = false;
                            }
                        }

                    }
                });
                boardPanel.add(tile);
            }
        }
        
        frame.setVisible(true);

        setMines();
        /* CustomCursor(); */

    }

  /*   public void CustomCursor() {
        Toolkit t1 = Toolkit.getDefaultToolkit();
        Image img = t1.getImage("C:\\Users\\lgeorg\\Desktop\\Minesweeper\\src\\121-1214814_deadpool-pixel-art-pixel-art-deadpool-clipart.jpg");
        Point p = new Point(0,0);
        Cursor c= t1.createCustomCursor(img, p, "C:\\Users\\lgeorg\\Desktop\\Minesweeper\\src\\121-1214814_deadpool-pixel-art-pixel-art-deadpool-clipart.jpg");
        Label n = new Label("labelone");
        n.setCursor(c);
        boardPanel.add(n);

    }  */

    void setMines() {
        mineList = new ArrayList<MineTile>();

        /* mineList.add(board[2][2]);
        mineList.add(board[2][3]);
        mineList.add(board[5][6]);
        mineList.add(board[3][4]);
        mineList.add(board[1][1]); */ //just for building
        int mineLeft = minecount;      //now lets make the Game funktional :P
        while (mineLeft > 0) {
            int r = random.nextInt(numrows);
            int c = random.nextInt(colums);

            MineTile tile = board[r][c];
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
    }

    void revealMines() throws IOException{
        for (int i = 0; i < mineList.size(); i ++) {
            MineTile tile = mineList.get(i);

            File file = new File("C:\\\\Users\\\\lgeorg\\\\Desktop\\\\Minesweeper\\\\src\\\\31484-4-atomic-explosion-photo.png");  
           
            BufferedImage bufferedImage = ImageIO.read(file);
            
            ImageIcon imageIcon = new ImageIcon(bufferedImage);

            textlabel.setText("GameOver");
            //textlabel.setIcon(imageIcon);
            
            tile.setIcon(imageIcon);

            File filo = new File("C:\\Users\\lgeorg\\Desktop\\Minesweeper\\src\\mine.png");  
           
            BufferedImage bufferedImago = ImageIO.read(filo);
            
            ImageIcon imageIco = new ImageIcon(bufferedImago);

            textlabel.setIcon(imageIco);
            tile.setEnabled(false);
 
        }

        GameOver = true;
    }

    void checkMine (int r, int c) {
        if (r < 0 || r >= numrows || c < 0 || c >= colums) {
            return;
        }
        

        MineTile tile = board [r][c];
        if (!tile.isEnabled()) {
            return;
        }
        //tile.setEnabled(false);
        tilesClicked += 1;

        int minesFound = 0;

        minesFound += countMine(r-1, c-1); //top left
        minesFound += countMine(r-1, c); //top
        minesFound += countMine(r-1, c+1); //topright

        //left and right
        minesFound += countMine(r, c-1); //left     
        minesFound += countMine(r, c+1); //right

        //bottom3
        minesFound += countMine(r+1, c-1); //left
        minesFound += countMine(r+1, c);   //bottom
        minesFound += countMine(r+1, c+1); //bottom right
        
        if (!mineList.contains(tile)) {
            tile.setEnabled(false);
        }

        if (minesFound > 0 ) {//&& !mineList.contains(tile)) {
            tile.setText(Integer.toString(minesFound));
        }
        else {
            tile.setText("");

            //check neighbours
            checkMine(r-1, c-1); //left
            checkMine(r-1, c); //top
            checkMine(r-1, c+1); //top right

            //left and right
            checkMine(r, c-1); //left
            checkMine(r, c+1); //right

            //bottom
            checkMine(r+1, c-1); //left
            checkMine(r+1, c); //bottom    
            checkMine(r+1, c+1); //right

        }

        if (tilesClicked == numrows * colums - mineList.size()) {
            GameOver = true;
            textlabel.setText("Mines detected!");
            /*JLabel taxlabel = new JLabel();
            JPanel taxpanel = new JPanel();
            taxlabel.setFont(new Font("Arial", Font.BOLD, 25) );
            taxlabel.setHorizontalAlignment(JLabel.CENTER);
            taxlabel.setText("You Won 💣");
            taxpanel.setLayout(new BorderLayout());
            taxpanel.add(taxlabel);
            taxpanel.getBackground();
            frame.add(taxpanel, BorderLayout.CENTER);*/
        }
    }

    int countMine(int r, int c) {
        if (r < 0 || r >= numrows || c < 0 || c >= colums) {
            return 0;
        }
        if (mineList.contains(board[r][c])) {
            return 1;
        }
        return 0;

    }
    
}
