/**
 * Learn classic Match-3-Game by myself
 * Created by Denis on 24.11.2016.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Match3Game {

    final String TITLE_OF_PROGRAM = "Match-3-Game";
    final int BALL_SIZE = 30;
    final int FIELD_WIDTH = 6; // in balls
    final int FIELD_HEIGHT = 6;
    final int FIELD_DX = 6;
    final int FIELD_DY = 28;
    final int START_LOCATION = 400;
    final int NUMBER_OF_COLORS = 3;
    final int SHOW_DELAY = 350;
    boolean gameOver = false;
    JFrame frame;
    Canvas canvasPanel;
    Random random = new Random();
    Delay delay = new Delay();
    Balls gameBalls;
    Color[] randomColors;

    public static void main(String[] args) {
        new Match3Game().go();
    }

    void go(){
        frame = new JFrame(TITLE_OF_PROGRAM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_WIDTH * BALL_SIZE + FIELD_DX, FIELD_HEIGHT * BALL_SIZE + FIELD_DY);
        frame.setLocation(START_LOCATION, START_LOCATION);
        frame.setResizable(false);

        canvasPanel = new Canvas();
        canvasPanel.setBackground(Color.black);
        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("clicked x " + e.getX()  + " y " + e.getY());
                Ball clickedBall = gameBalls.whoIsClicked(e.getX(), e.getY());
                System.out.println("balls x " + clickedBall.x  + " y " + clickedBall.y + " color " + clickedBall.color.toString());
            }
        });

        frame.setVisible(true);

        randomColors = new Color[FIELD_HEIGHT * FIELD_WIDTH];
        for (int i = 0; i < FIELD_HEIGHT * FIELD_WIDTH; i++) {
            randomColors[i] = getRandomColor();
        }
        gameBalls = new Balls();
        while (!gameOver) {
            delay.wait(SHOW_DELAY);
        }
    }

    class Ball {
        int x, y;
        Color color = Color.WHITE;

        public Ball(int x, int y){
            this.x = x;
            this.y = y;
//            this.color = Color.GREEN;//getRandomColor();
//            this.color = getRandomColor();
//            System.out.println(this.color.toString());
        }

        boolean isClicked(int x, int y) {
            if (x >= this.x*BALL_SIZE+FIELD_DX-2 && x <= this.x*BALL_SIZE+BALL_SIZE+FIELD_DX-2 && y >= this.y*BALL_SIZE+FIELD_DY-2 && y <= this.y*BALL_SIZE+BALL_SIZE+FIELD_DY-2) {
                return true;
            }
            return false;
        }

        void paint(Graphics g) {
            g.setColor(color);
            g.fillOval(x * BALL_SIZE, y * BALL_SIZE, BALL_SIZE, BALL_SIZE);
        }
    }

    public Color getRandomColor() {
        int type = random.nextInt(NUMBER_OF_COLORS)+1;
        switch (type) {
            case 1:
                return Color.RED;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.BLUE;
            default:
                return Color.WHITE;
        }
    }

    class Balls {
        Ball[][] balls;

        public Balls() {
            balls = new Ball[FIELD_HEIGHT][FIELD_WIDTH];
            int i = 0;
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                for (int x = 0; x < FIELD_WIDTH; x++) {
                    balls[y][x] = new Ball(x, y);
                    balls[y][x].color = randomColors[i++];
                }
            }
        }

        Ball whoIsClicked(int clickedX, int clickedY) {
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                for (int x = 0; x < FIELD_WIDTH; x++) {
                    if (balls[y][x].isClicked(clickedX, clickedY)) {
                        return balls[y][x];
                    }
                }
            }
            return null;
        }

        void paint(Graphics g) {
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                for (int x = 0; x < FIELD_WIDTH; x++) {
                    balls[y][x].paint(g);
                }
            }
        }
    }

    class Delay {
        void wait(int milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            gameBalls.paint(g);
        }
    }
}
