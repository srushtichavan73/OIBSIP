package com.reservation.gui;

import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {

    public MainDashboard() {

        setTitle("Online Reservation System - Dashboard");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        JLabel title = new JLabel(
                "ONLINE RESERVATION SYSTEM",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel =
                new JPanel(new GridLayout(3, 1, 15, 15));

        JButton reservationButton =
                new JButton("Make Reservation");

        JButton cancellationButton =
                new JButton("Cancel Reservation");

        JButton logoutButton =
                new JButton("Logout");

        buttonPanel.add(reservationButton);
        buttonPanel.add(cancellationButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        reservationButton.addActionListener(e -> {
            new ReservationFrame();
            dispose();
        });

        cancellationButton.addActionListener(e -> {
            new CancellationFrame();
            dispose();
        });

        logoutButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        add(mainPanel);

        setVisible(true);
    }
}