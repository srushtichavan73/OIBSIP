package com.reservation.gui;

import javax.swing.*;
import java.awt.*;

public class BookingConfirmationFrame extends JFrame {

    public BookingConfirmationFrame(
            String pnr,
            String passengerName,
            String trainName,
            String source,
            String destination,
            String journeyDate) {

        setTitle("Booking Confirmation");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("RESERVATION CONFIRMED");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(title);
        panel.add(Box.createVerticalStrut(25));

        panel.add(new JLabel("PNR: " + pnr));
        panel.add(new JLabel("Passenger Name: " + passengerName));
        panel.add(new JLabel("Train: " + trainName));
        panel.add(new JLabel("From: " + source));
        panel.add(new JLabel("To: " + destination));
        panel.add(new JLabel("Journey Date: " + journeyDate));

        panel.add(Box.createVerticalStrut(25));

        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        closeButton.addActionListener(e -> dispose());

        panel.add(closeButton);

        add(panel);

        setVisible(true);
    }
}
