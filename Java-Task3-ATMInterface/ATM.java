package com.atm;

import java.util.HashMap;
import java.util.Map;

public class ATM {

    private User currentUser;
    private Account currentAccount;

    private Map<String, User> users;
    private Map<String, Account> accounts;

    public ATM() {

        users = new HashMap<>();
        accounts = new HashMap<>();

        // Sample accounts
        addUser(
                new User("Srushti", "1001", 1234),
                new Account(10000)
        );

        addUser(
                new User("Rahul", "1002", 5678),
                new Account(15000)
        );

        addUser(
                new User("Priya", "1003", 4321),
                new Account(20000)
        );
    }

    // Add user and account
    public void addUser(User user, Account account) {

        users.put(user.getAccountNumber(), user);
        accounts.put(user.getAccountNumber(), account);
    }

    // Find user
    public User findUser(String accountNumber) {

        return users.get(accountNumber);
    }

    // Find account
    public Account findAccount(String accountNumber) {

        return accounts.get(accountNumber);
    }

    // Set currently logged-in user
    public void setCurrentUser(String accountNumber) {

        currentUser = findUser(accountNumber);
        currentAccount = findAccount(accountNumber);
    }

    // Get current user
    public User getCurrentUser() {

        return currentUser;
    }

    // Get current account
    public Account getCurrentAccount() {

        return currentAccount;
    }

    // Check balance
    public void checkBalance() {

        System.out.println("\n----- ACCOUNT BALANCE -----");

        System.out.println(
                "Account Holder : " + currentUser.getName()
        );

        System.out.println(
                "Account Number : " + currentUser.getAccountNumber()
        );

        System.out.println(
                "Available Balance : ₹" +
                        currentAccount.getBalance()
        );
    }

    // Deposit money
    public void deposit(double amount) {

        if (amount <= 0) {

            System.out.println("Invalid deposit amount!");
            return;
        }

        currentAccount.deposit(amount);

        currentAccount.addTransaction(
                new Transaction(
                        "Deposit",
                        amount,
                        currentAccount.getBalance()
                )
        );

        System.out.println(
                "\n₹" + amount +
                        " deposited successfully."
        );

        System.out.println(
                "Current Balance: ₹" +
                        currentAccount.getBalance()
        );

        // Print receipt
        printReceipt("Deposit", amount);
    }

    // Withdraw money
    public void withdraw(double amount) {

        if (amount <= 0) {

            System.out.println("Invalid withdrawal amount!");
            return;
        }

        if (currentAccount.withdraw(amount)) {

            currentAccount.addTransaction(
                    new Transaction(
                            "Withdrawal",
                            amount,
                            currentAccount.getBalance()
                    )
            );

            System.out.println(
                    "\n₹" + amount +
                            " withdrawn successfully."
            );

            System.out.println(
                    "Current Balance: ₹" +
                            currentAccount.getBalance()
            );

            // Print receipt
            printReceipt("Withdrawal", amount);

        } else {

            System.out.println(
                    "\nInsufficient balance!"
            );
        }
    }

    // Transfer money
    public void transfer(
            String receiverAccountNumber,
            double amount) {

        if (amount <= 0) {

            System.out.println(
                    "Invalid transfer amount!"
            );

            return;
        }

        Account receiverAccount =
                findAccount(receiverAccountNumber);

        User receiver =
                findUser(receiverAccountNumber);

        // Check receiver
        if (receiverAccount == null || receiver == null) {

            System.out.println(
                    "Receiver account not found!"
            );

            return;
        }

        // Prevent self-transfer
        if (receiverAccountNumber.equals(
                currentUser.getAccountNumber())) {

            System.out.println(
                    "You cannot transfer money to your own account!"
            );

            return;
        }

        // Withdraw from sender
        if (currentAccount.withdraw(amount)) {

            // Deposit to receiver
            receiverAccount.deposit(amount);

            // Add transaction to sender history
            currentAccount.addTransaction(
                    new Transaction(
                            "Transfer to " +
                                    receiver.getName() +
                                    " (" +
                                    receiverAccountNumber +
                                    ")",
                            amount,
                            currentAccount.getBalance()
                    )
            );

            System.out.println(
                    "\n----- TRANSFER SUCCESSFUL -----"
            );

            System.out.println(
                    "To Account : " +
                            receiverAccountNumber
            );

            System.out.println(
                    "Receiver : " +
                            receiver.getName()
            );

            System.out.println(
                    "Amount : ₹" + amount
            );

            System.out.println(
                    "Remaining Balance : ₹" +
                            currentAccount.getBalance()
            );

            // Print receipt
            printReceipt(
                    "Transfer to " + receiver.getName(),
                    amount
            );

        } else {

            System.out.println(
                    "\nInsufficient balance!"
            );
        }
    }

    // Show transaction history
    public void showTransactionHistory() {

        System.out.println(
                "\n----- TRANSACTION HISTORY -----"
        );

        if (currentAccount.getTransactions().isEmpty()) {

            System.out.println(
                    "No transactions available."
            );

            return;
        }

        for (Transaction transaction :
                currentAccount.getTransactions()) {

            System.out.println(transaction);
        }
    }

    // Transaction receipt
    private void printReceipt(
            String transactionType,
            double amount) {

        System.out.println(
                "\n================================"
        );

        System.out.println(
                "       TRANSACTION RECEIPT"
        );

        System.out.println(
                "================================"
        );

        System.out.println(
                "Account Holder : " +
                        currentUser.getName()
        );

        System.out.println(
                "Account Number : " +
                        currentUser.getAccountNumber()
        );

        System.out.println(
                "Transaction    : " +
                        transactionType
        );

        System.out.println(
                "Amount         : ₹" +
                        amount
        );

        System.out.println(
                "Balance        : ₹" +
                        currentAccount.getBalance()
        );

        System.out.println(
                "================================"
        );
    }
}