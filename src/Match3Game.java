/**
 * Learn classic Match-3-Game by myself
 * Created by Denis on 24.11.2016.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Match3Game {

    final String TITLE_OF_PROGRAM = "Match-3-Game";
    final int BALL_RADIUS = 30;
    final int FIELD_WIDTH = 6; // in balls
    final int FIELD_HEIGHT = 6;
    final int FIELD_DX = 6;
    final int FIELD_DY = 28;
    final int START_LOCATION = 400;
    JFrame frame;
    Canvas canvasPanel = new Canvas();
    Random random = new Random();

    public static void main(String[] args) {
        new Match3Game().go();
    }

    void go(){
        frame = new JFrame(TITLE_OF_PROGRAM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_WIDTH * BALL_RADIUS + FIELD_DX, FIELD_HEIGHT * BALL_RADIUS + FIELD_DY);
        frame.setLocation(START_LOCATION, START_LOCATION);
        frame.setResizable(false);

        canvasPanel.setBackground(Color.black);
        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);

        frame.setVisible(true);
    }

    public class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }
}
