package org.spiral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Prime Spiral");

        PrimeSpiral primeSpiral = new PrimeSpiral();
        InstantPrimeSpiral instantPrimeSpiral = new InstantPrimeSpiral();

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.BLACK);
        controlPanel.setForeground(Color.WHITE);

        JTextField primeInputField = new JTextField(10);
        primeInputField.setMaximumSize(new Dimension(100, 25));
        primeInputField.setBackground(Color.WHITE);
        primeInputField.setForeground(Color.BLACK);

        JTextField intervalInputField = new JTextField(10);
        intervalInputField.setMaximumSize(new Dimension(100, 25));
        intervalInputField.setBackground(Color.WHITE);
        intervalInputField.setForeground(Color.BLACK);


        JButton drawButton = new JButton("Draw Primes");
        JButton drawInstantlyButton = new JButton("Draw Instantly");
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numPrimes = Integer.parseInt(primeInputField.getText());
                    int interval = Integer.parseInt(intervalInputField.getText());
                    primeSpiral.setDrawInterval(interval);
                    primeSpiral.updateGraph(numPrimes);
                    frame.getContentPane().removeAll();
                    frame.add(primeSpiral, BorderLayout.CENTER);
                    frame.add(controlPanel, BorderLayout.EAST);
                    frame.revalidate();
                    frame.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid number format. Please enter valid integers.");
                }
            }
        });

        drawInstantlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numPrimes = Integer.parseInt(primeInputField.getText());
                    instantPrimeSpiral.updateGraphInstantly(numPrimes);
                    frame.getContentPane().removeAll();
                    frame.add(instantPrimeSpiral, BorderLayout.CENTER);
                    frame.add(controlPanel, BorderLayout.EAST);
                    frame.revalidate();
                    frame.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid number format. Please enter valid integers.");
                }
            }
        });

        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (instantPrimeSpiral != null) {
                    instantPrimeSpiral.setZoomLevel(Math.max(instantPrimeSpiral.zoomLevel - 1, 1));
                }
                if (primeSpiral != null) {
                    primeSpiral.setZoomLevel(Math.max(primeSpiral.zoomLevel - 1, 1));
                }
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (instantPrimeSpiral != null) {
                    instantPrimeSpiral.setZoomLevel(instantPrimeSpiral.zoomLevel + 1);
                }
                if (primeSpiral != null) {
                    primeSpiral.setZoomLevel(primeSpiral.zoomLevel + 1);
                }
            }
        });

        JLabel primesLabel = new JLabel("Number of Primes:");
        primesLabel.setForeground(Color.WHITE);
        controlPanel.add(primesLabel);

        JLabel intervalLabel = new JLabel("Interval (ms):");
        intervalLabel.setForeground(Color.WHITE);

        controlPanel.add(primeInputField);
        controlPanel.add(intervalLabel);
        controlPanel.add(intervalInputField);
        controlPanel.add(drawButton);
        controlPanel.add(drawInstantlyButton);
        controlPanel.add(zoomInButton);
        controlPanel.add(zoomOutButton);

        frame.setLayout(new BorderLayout());
        frame.add(primeSpiral, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.EAST);

        frame.getContentPane().setBackground(Color.BLACK);
        frame.getContentPane().setForeground(Color.WHITE);

        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
