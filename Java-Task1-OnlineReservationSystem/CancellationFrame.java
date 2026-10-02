package com.reservation.gui;

import com.reservation.database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CancellationFrame extends JFrame {

    private JTextField pnrField;
    private JButton searchButton;
    private JButton cancelButton;

    public CancellationFrame() {

        setTitle("Online Reservation System - Cancellation");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Enter PNR:"));

        pnrField = new JTextField();
        panel.add(pnrField);

        searchButton = new JButton("Search");
        panel.add(new JLabel());
        panel.add(searchButton);

        cancelButton = new JButton("Cancel Reservation");
        cancelButton.setEnabled(false);
        panel.add(new JLabel());
        panel.add(cancelButton);

        add(panel);

        searchButton.addActionListener(e -> searchReservation());

        cancelButton.addActionListener(e -> cancelReservation());

        setVisible(true);
    }

    private void searchReservation() {

        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a PNR!"
            );

            return;
        }

        String sql =
                "SELECT * FROM reservations WHERE pnr = ?";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, pnr);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                String details =
                        "Passenger: "
                                + resultSet.getString("passenger_name")
                                + "\nTrain: "
                                + resultSet.getString("train_name")
                                + "\nFrom: "
                                + resultSet.getString("source")
                                + "\nTo: "
                                + resultSet.getString("destination")
                                + "\nJourney Date: "
                                + resultSet.getDate("journey_date");

                JOptionPane.showMessageDialog(
                        this,
                        details,
                        "Reservation Found",
                        JOptionPane.INFORMATION_MESSAGE
                );

                cancelButton.setEnabled(true);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No reservation found for this PNR."
                );

                cancelButton.setEnabled(false);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage()
            );
        }
    }

    private void cancelReservation() {

        String pnr = pnrField.getText().trim();

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel this reservation?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM reservations WHERE pnr = ?";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, pnr);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reservation cancelled successfully!"
                );

                pnrField.setText("");
                cancelButton.setEnabled(false);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Reservation could not be cancelled."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cancellation failed: " + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        new CancellationFrame();
    }
}