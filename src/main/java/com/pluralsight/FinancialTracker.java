package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

//import static FinancialTracker.DATE_FORMATTER;
//import static FinancialTracker.DATE_FORMATTER;

public class FinancialTracker {


    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    Scanner scanner =new Scanner(System.in);


    public static void main(String[] args) {
       loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String filename) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = bufferedReader.readLine()) !=null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String dateString = parts[0].trim();
                    LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
                    LocalTime time = LocalTime.parse(parts[1], TIME_FORMATTER);
                    String type = parts[2].trim();
                    String vendor = parts[3].trim();
                    double amount = Double.parseDouble(parts[4]);
                    transactions.add(new Transaction(date, time, type, vendor, amount));
                }
            }

            bufferedReader.close();

        } catch (Exception e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }



        }




    private static void addDeposit(Scanner scanner) {
        System.out.println("Please enter the date(yyyy-MM-dd): ");
        String Date = scanner.nextLine();
        LocalDate dateString = LocalDate.parse(Date, DATE_FORMATTER);
        System.out.println("Please enter the time (HH:mm:ss): ");
        String timeString = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeString, TIME_FORMATTER);
        System.out.println("Please enter the type: ");
        String type = scanner.nextLine();
        System.out.println("Please enter the vendor: ");
        String vendor = scanner.nextLine();
        System.out.println("Please enter your deposit amount: ");
        double depositAmount = scanner.nextDouble();


        try {
            if (depositAmount <= 0) {

                System.out.println("amount should be a positive number ");
            }
        } catch (NumberFormatException e) {
            System.out.println("invalid amount format. Please enter the valid number ");
            return;
        }

        Transaction deposit = new Transaction(dateString, time, type , vendor, depositAmount);
        transactions.add(deposit);

        FileWriter writer = null;
        try {
            writer = new FileWriter(FILE_NAME, true);
            writer.write(dateString.toString()+"|"+timeString.toString()+"|"+type+"|"+vendor+"|"+depositAmount);
            writer.close();
        } catch (Exception e) {
            System.out.println("Please enter again, invalid!" );
        }

    }


        // This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Deposit` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.


    private static void addPayment(Scanner scanner) {
        System.out.println("Please enter the date(yyyy-MM-dd): ");
        String Date = scanner.nextLine();
        LocalDate dateString = LocalDate.parse(Date, DATE_FORMATTER);
        System.out.println("Please enter the time (HH:mm:ss): ");
        String timeString = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeString, TIME_FORMATTER);
        System.out.println("Please enter the type: ");
        String type = scanner.nextLine();
        System.out.println("Please enter the vendor: ");
        String vendor = scanner.nextLine();
        System.out.println("Please enter your deposit amount: ");
        double paymentAmount = scanner.nextDouble();
        paymentAmount = -paymentAmount;
        Transaction payment = (new Transaction(dateString, time, type, vendor, paymentAmount));
        transactions.add(payment);
    }





        private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, type, and amount.
        System.out.println("type in all the lists");
        for (Transaction transac : transactions) {
            System.out.println(transac.getDate() + "|" + transac.getTime() + "|" + transac.getType() + "|" + transac.getVendor() + "|" + transac.getAmount() + "|");

        }
    }
    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.

    }

    private static void displayPayments() {
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    // Generate a report for all transactions within the current month,
                    // including the date, vendor, and amount for each transaction.
                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, vendor, and amount for each transaction.
                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, vendor, and amount for each transaction.

                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, vendor, and amount for each transaction.
                case "5":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, vendor, and amount for each transaction.
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.
    }

    private static void filterTransactionsByVendor(String vendor) {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
    }
}