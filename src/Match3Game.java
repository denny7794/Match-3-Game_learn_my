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
    JFrame frame;
    Canvas canvasPanel = new Canvas();
    Random random = new Random();

    public static void main(String[] args) {
        new Match3Game().go();
    }

    void go(){
        frame = new JFrame(TITLE_OF_PROGRAM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_WIDTH * BALL_SIZE + FIELD_DX, FIELD_HEIGHT * BALL_SIZE + FIELD_DY);
        frame.setLocation(START_LOCATION, START_LOCATION);
        frame.setResizable(false);

        canvasPanel.setBackground(Color.black);
        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);

        frame.setVisible(true);
    }

    class Ball {
        int x, y;
        Color color;

        public Ball(int x, int y){
            this.x = x;
            this.y = y;
            this.color = getRandomColor();
        }

        Color getRandomColor() {
            Color color;
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

    public class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }
}
