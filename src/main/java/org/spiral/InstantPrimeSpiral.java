package org.spiral;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class InstantPrimeSpiral extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int DEFAULT_RADIUS = 300;
    private static final int POINT_RADIUS = 3;

    private Set<Long> primes = new TreeSet<>();
    private List<Long> primeList = new ArrayList<>();
    private int drawInterval = 0; // Ignored for instant drawing
    private boolean drawing = false;
    int zoomLevel = 1; // Default zoom level

    public InstantPrimeSpiral() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
        repaint();
    }

    public void generatePrimes(long limit) {
        boolean[] isPrime = new boolean[(int) limit + 1];
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }
        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = (int) i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        primes.clear();
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                primes.add((long) i);
            }
        }
        primeList = new ArrayList<>(primes);
    }

    public void updateGraphInstantly(int numPrimes) {
        generatePrimes(numPrimes);
        drawing = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));

        drawRadialGraph(g2d);

        g2d.setColor(Color.RED);
        for (Long prime : primeList) {
            drawPoint(g2d, prime);
        }
        drawing = false;
    }

    private void drawRadialGraph(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);

        int numCircles = 10;
        int maxRadius = DEFAULT_RADIUS / zoomLevel;
        for (int i = 1; i <= numCircles; i++) {
            int radius = i * (maxRadius / numCircles);
            g2d.drawOval(WIDTH / 2 - radius, HEIGHT / 2 - radius, radius * 2, radius * 2);
        }

        int numLines = 36;
        double angleStep = Math.PI * 2 / numLines;
        for (int i = 0; i < numLines; i++) {
            double angle = i * angleStep;
            int x2 = (int) (WIDTH / 2 + maxRadius * Math.cos(angle));
            int y2 = (int) (HEIGHT / 2 - maxRadius * Math.sin(angle));
            g2d.drawLine(WIDTH / 2, HEIGHT / 2, x2, y2);
        }
    }

    private void drawPoint(Graphics2D g2d, long prime) {
        double angle = Math.toRadians(prime % 360);
        double radius = prime % (DEFAULT_RADIUS / zoomLevel - POINT_RADIUS);
        int x = (int) (WIDTH / 2 + radius * Math.cos(angle));
        int y = (int) (HEIGHT / 2 - radius * Math.sin(angle));
        g2d.fillOval(x - POINT_RADIUS, y - POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2);
    }
}
