package com.atm;

public class User {

    private String name;
    private String accountNumber;
    private int pin;

    public User(String name, String accountNumber, int pin) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getPin() {
        return pin;
    }
}