package com.atm;

import java.util.Scanner;

public class ATMInterface {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ATM atm = new ATM();

        System.out.println("================================");
        System.out.println("          ATM INTERFACE");
        System.out.println("================================");

        // Account number
        System.out.print("Enter Account Number: ");
        String accountNumber = sc.nextLine();

        User user = atm.findUser(accountNumber);

        if (user == null) {

            System.out.println(
                    "\nAccount not found!"
            );

            sc.close();
            return;
        }

        // PIN verification
        boolean pinCorrect = false;

        for (int attempt = 1; attempt <= 3; attempt++) {

            System.out.print("Enter your PIN: ");
            int enteredPin = sc.nextInt();

            if (enteredPin == user.getPin()) {

                pinCorrect = true;

                System.out.println(
                        "\nPIN verified successfully!"
                );

                break;

            } else {

                System.out.println(
                        "Incorrect PIN! Attempts remaining: "
                                + (3 - attempt)
                );
            }
        }

        // Block after 3 wrong attempts
        if (!pinCorrect) {

            System.out.println(
                    "\nToo many incorrect attempts."
            );

            System.out.println(
                    "Account access blocked."
            );

            sc.close();
            return;
        }

        // Set current user
        atm.setCurrentUser(accountNumber);

        System.out.println(
                "\nWelcome, "
                        + atm.getCurrentUser().getName()
                        + "!"
        );

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("           ATM MENU");
            System.out.println("================================");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Transaction History");
            System.out.println("6. Quit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    atm.checkBalance();
                    break;

                case 2:

                    System.out.print(
                            "Enter deposit amount: ₹"
                    );

                    double depositAmount =
                            sc.nextDouble();

                    atm.deposit(depositAmount);
                    break;

                case 3:

                    System.out.print(
                            "Enter withdrawal amount: ₹"
                    );

                    double withdrawalAmount =
                            sc.nextDouble();

                    atm.withdraw(withdrawalAmount);
                    break;

                case 4:

                    sc.nextLine();

                    System.out.print(
                            "Enter receiver account number: "
                    );

                    String receiverAccount =
                            sc.nextLine();

                    System.out.print(
                            "Enter transfer amount: ₹"
                    );

                    double transferAmount =
                            sc.nextDouble();

                    atm.transfer(
                            receiverAccount,
                            transferAmount
                    );

                    break;

                case 5:

                    atm.showTransactionHistory();
                    break;

                case 6:

                    System.out.println(
                            "\n================================"
                    );

                    System.out.println(
                            "Thank you for using the ATM!"
                    );

                    System.out.println(
                            "Have a nice day!"
                    );

                    System.out.println(
                            "================================"
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice! Please try again."
                    );
            }

        } while (choice != 6);

        sc.close();
    }
}

















