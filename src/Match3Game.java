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
    final int NUMBER_OF_COLORS = 5;
    final int SHOW_DELAY = 350;
    boolean gameOver = false;
    boolean firstClick = false, secondClick = false;
    int countBallsX = 1, countBallsY = 1;
    JFrame frame;
    Canvas canvasPanel;
    Random random = new Random();
    Delay delay = new Delay();
    Balls gameBalls, linedBalls;//X, linedBallsY;
    Ball firstBall, secondBall;
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
                if (!firstClick) {
                    firstClick = true;
                    firstBall = new Ball(clickedBall.x, clickedBall.y, clickedBall.color);
                } else {
                    if (firstBall.y == clickedBall.y && (firstBall.x - 1 == clickedBall.x || firstBall.x + 1 == clickedBall.x) || firstBall.x == clickedBall.x && (firstBall.y - 1 == clickedBall.y || firstBall.y + 1 == clickedBall.y)) {
                        /*Color tempColor = clickedBall.color;
                        clickedBall.color = firstBall.color;
                        firstBall.color = tempColor;*/
                        /*Ball tempBall;
                        tempBall = new Ball(clickedBall.x, clickedBall.y, clickedBall.color);
                        gameBalls[clickedBall.y][clickedBall.x] = new Ball(firstBall.x, firstBall.y, firstBall.color);
                        gameBalls[firstBall.y][firstBall.x] = new Ball(tempBall.x, tempBall.y, tempBall.color);*/
                        gameBalls.exchange(firstBall, clickedBall);
                        canvasPanel.repaint();
                        gameBalls.checkLines();
                        firstClick = false;
                    } else {
                        firstBall = new Ball(clickedBall.x, clickedBall.y, clickedBall.color);
                    }
                }


            }
        });

        frame.setVisible(true);

        randomColors = new Color[FIELD_HEIGHT * FIELD_WIDTH];
        for (int i = 0; i < FIELD_HEIGHT * FIELD_WIDTH; i++) {
            randomColors[i] = getRandomColor();
        }
        gameBalls = new Balls();
        linedBalls = new Balls();
//        linedBallsY = new Balls();
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

        public Ball(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
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
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.CYAN;
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

        void zeroInint(){
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                for (int x = 0; x < FIELD_WIDTH; x++) {
                    balls[y][x] = null;
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

        void exchange(Ball firstBall, Ball secondBall) {
            Ball tempBall = new Ball(0,0);
            /*tempBall = new Ball(secondBall.x, secondBall.y, secondBall.color);
            balls[secondBall.y][secondBall.x] = new Ball(firstBall.x, firstBall.y, firstBall.color);
            balls[firstBall.y][firstBall.x] = new Ball(tempBall.x, tempBall.y, tempBall.color);*/
            tempBall.color = secondBall.color;
            balls[secondBall.y][secondBall.x].color = balls[firstBall.y][firstBall.x].color;
            balls[firstBall.y][firstBall.x].color = tempBall.color;
        }

        void checkLines() {
            linedBalls.zeroInint();
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                int x = 0;
                while (x < FIELD_WIDTH-2) {
                    checkHorizontalLines(x, y);
                    if (countBallsX >= 3) {
                        for (int i = x; i < x+countBallsX; i++) {
//                            balls[y][i].color = Color.WHITE;
                            linedBalls.balls[y][i] = new Ball(y, i, Color.WHITE);
                        }
                    }
                    x = x + countBallsX;
                    countBallsX = 1;
                }
            }
            for (int x = 0; x < FIELD_WIDTH; x++) {
                int y = 0;
                while (y < FIELD_HEIGHT-2) {
                    checkVerticalLines(x, y);
                    if (countBallsY >= 3) {
                        for (int i = y; i < y+countBallsY; i++) {
//                            balls[y][i].color = Color.WHITE;
                            linedBalls.balls[i][x] = new Ball(i, x, Color.WHITE);
                        }
                    }
                    y = y + countBallsY;
                    countBallsY = 1;
                }
            }


            for (int y = 0; y < FIELD_HEIGHT; y++) {
                for (int x = 0; x < FIELD_WIDTH; x++) {
                    if (linedBalls.balls[y][x] != null){
                        balls[y][x].color = Color.WHITE;
                    }
                }
            }
        }

        void checkHorizontalLines(int x, int y){
            if (balls[y][x].x < FIELD_WIDTH-1) {
                if (balls[y][x].color == balls[y][x + 1].color) {
                    countBallsX++;
                    checkHorizontalLines(x + 1, y);
                } else {
                    return;
                }
            }
        }
        void checkVerticalLines(int x, int y){
            if (balls[y][x].y < FIELD_HEIGHT-1) {
                if (balls[y][x].color == balls[y+1][x].color) {
                    countBallsY++;
                    checkVerticalLines(x, y+1);
                } else {
                    return;
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
