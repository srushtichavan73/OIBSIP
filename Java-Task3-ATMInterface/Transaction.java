package com.atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String type;
    private double amount;
    private double balance;
    private LocalDateTime dateTime;

    public Transaction(String type, double amount, double balance) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return "Type: " + type
                + " | Amount: ₹" + amount
                + " | Balance: ₹" + balance
                + " | Date: " + dateTime.format(formatter);
    }
}