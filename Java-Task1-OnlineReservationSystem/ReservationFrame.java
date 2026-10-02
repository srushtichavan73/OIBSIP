package com.reservation.gui;

import com.reservation.database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class ReservationFrame extends JFrame {

    private JTextField passengerNameField;
    private JTextField ageField;
    private JComboBox<String> genderBox;
    private JComboBox<String> trainBox;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextField dateField;

    public ReservationFrame() {

        setTitle("Online Reservation System - Reservation");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        panel.add(new JLabel("Passenger Name:"));
        passengerNameField = new JTextField();
        panel.add(passengerNameField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(
                new String[]{"Male", "Female", "Other"}
        );
        panel.add(genderBox);

        panel.add(new JLabel("Train:"));
        trainBox = new JComboBox<>();
        panel.add(trainBox);

        panel.add(new JLabel("Source:"));
        sourceField = new JTextField();
        sourceField.setEditable(false);
        panel.add(sourceField);

        panel.add(new JLabel("Destination:"));
        destinationField = new JTextField();
        destinationField.setEditable(false);
        panel.add(destinationField);

        panel.add(new JLabel("Journey Date:"));
        dateField = new JTextField();
        panel.add(dateField);

        JButton reserveButton =
                new JButton("Make Reservation");

        panel.add(new JLabel());
        panel.add(reserveButton);

        add(panel);

        loadTrains();

        trainBox.addActionListener(e -> updateRoute());

        reserveButton.addActionListener(e -> makeReservation());

        setVisible(true);
    }

    private void loadTrains() {

        String sql =
                "SELECT train_number, train_name, source, destination FROM trains";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                String trainNumber =
                        resultSet.getString("train_number");

                String trainName =
                        resultSet.getString("train_name");

                String source =
                        resultSet.getString("source");

                String destination =
                        resultSet.getString("destination");

                String trainDetails =
                        trainNumber + " - " + trainName
                                + " (" + source
                                + " → " + destination + ")";

                trainBox.addItem(trainDetails);
            }

            updateRoute();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load trains: "
                            + e.getMessage()
            );
        }
    }

    private void updateRoute() {

        String selectedTrain =
                (String) trainBox.getSelectedItem();

        if (selectedTrain == null) {
            return;
        }

        String remaining =
                selectedTrain.substring(
                        selectedTrain.indexOf(" - ") + 3
                );

        String route =
                remaining.substring(
                        remaining.indexOf("(") + 1,
                        remaining.indexOf(")")
                );

        String[] locations =
                route.split(" → ");

        if (locations.length == 2) {

            sourceField.setText(locations[0]);
            destinationField.setText(locations[1]);
        }
    }

    private void makeReservation() {

        String passengerName =
                passengerNameField.getText().trim();

        String ageText =
                ageField.getText().trim();

        String dateText =
                dateField.getText().trim();

        if (passengerName.isEmpty()
                || ageText.isEmpty()
                || dateText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields!"
            );

            return;
        }

        int age;

        try {

            age = Integer.parseInt(ageText);

            if (age <= 0 || age > 120) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid age!"
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number!"
            );

            return;
        }

        String selectedTrain =
                (String) trainBox.getSelectedItem();

        if (selectedTrain == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a train!"
            );

            return;
        }

        String trainNumber =
                selectedTrain.substring(
                        0,
                        selectedTrain.indexOf(" - ")
                );

        String remaining =
                selectedTrain.substring(
                        selectedTrain.indexOf(" - ") + 3
                );

        String trainName =
                remaining.substring(
                        0,
                        remaining.indexOf(" (")
                );

        String source =
                sourceField.getText();

        String destination =
                destinationField.getText();

        String gender =
                (String) genderBox.getSelectedItem();

        String pnr =
                "PNR"
                        + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        String sql = """
                INSERT INTO reservations
                (pnr, username, train_number, train_name,
                 passenger_name, age, gender, journey_date,
                 source, destination)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, pnr);
            statement.setString(2, "admin");
            statement.setString(3, trainNumber);
            statement.setString(4, trainName);
            statement.setString(5, passengerName);
            statement.setInt(6, age);
            statement.setString(7, gender);
            statement.setDate(8, Date.valueOf(dateText));
            statement.setString(9, source);
            statement.setString(10, destination);

            statement.executeUpdate();

            new BookingConfirmationFrame(
                    pnr,
                    passengerName,
                    trainName,
                    source,
                    destination,
                    dateText
            );
        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter date in YYYY-MM-DD format."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation failed: "
                            + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        new ReservationFrame();
    }
}