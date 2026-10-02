package com.atm;

import java.util.ArrayList;

public class Account {

    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(double balance) {
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        balance = balance + amount;
    }

    public boolean withdraw(double amount) {

        if (amount > balance) {
            return false;
        }

        balance = balance - amount;
        return true;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}