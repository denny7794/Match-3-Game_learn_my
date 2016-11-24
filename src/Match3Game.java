/**
 * Learn classic Match-3-Game by myself
 * Created by Denis on 24.11.2016.
 */

import java.awt.*;
import javax.swing.*;
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

        frame.setVisible(true);

        gameBalls = new Balls();
        while (!gameOver) {
            delay.wait(SHOW_DELAY);
        }
    }

    class Ball {
        int x, y;
        Color color;

        public Ball(int x, int y){
            this.x = x;
            this.y = y;
            this.color = Color.WHITE;//getRandomColor();
        }

        Color getRandomColor() {
            int type = random.nextInt(NUMBER_OF_COLORS-1)+1;
            switch (type) {
                case 1:
                    return Color.RED;
                case 2:
                    return Color.GREEN;
                case 3:
                    return Color.BLUE;
                default:
                    return Color.BLACK;
            }
        }

        void paint(Graphics g) {
            g.setColor(color);
            g.fillOval(x * BALL_SIZE, y * BALL_SIZE, BALL_SIZE, BALL_SIZE);
        }
    }

    class Balls {
        Ball[][] balls;

        public Balls() {
            balls = new Ball[FIELD_HEIGHT][FIELD_WIDTH];
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                for (int x = 0; x < FIELD_WIDTH; x++) {
                    balls[y][x] = new Ball(x, y);
                }
            }
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
