package com.mnz.game.entity.star;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class StarArrayFrame extends JFrame {

    private static final int WIDTH = 1800; // Width of the frame
    private static final int HEIGHT = 1200; // Height of the frame
    private static final int NUM_CIRCLES = 400; // Number of circles in the array

    public StarArrayFrame() {
        this.setTitle("Circle Array Example");
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Create a custom JPanel for drawing
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK); // Set the background color to black
        this.add(panel);

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        // Loop through and draw small circles
        for (int i = 0; i < NUM_CIRCLES; i++) {
            int x = (int) (Math.random() * WIDTH); // Random X coordinate
            int y = (int) (Math.random() * HEIGHT); // Random Y coordinate
            Random random = new Random();
            int diameter = random.nextInt(7) + 1;

            g2d.setColor(Color.white); // Set the circle color
            g2d.fillOval(x, y, diameter, diameter); // Draw the circle
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StarArrayFrame();
        });
    }
}

