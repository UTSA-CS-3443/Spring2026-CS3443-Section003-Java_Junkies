package com.budgetwise.controller;

import com.budgetwise.model.Transaction;
import com.budgetwise.model.BudgetCategory;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataManager {

    private static final String TRANSACTIONS_FILE = "data/transactions.csv";
    private static final String BUDGET_FILE = "data/budget_goals.csv";

    // ---------------- TRANSACTIONS ----------------

    public static void saveTransaction(Transaction transaction) {
        try {
            FileWriter writer = new FileWriter(TRANSACTIONS_FILE, true);
            writer.write(transaction.toCSV() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length == 5) {
                    transactions.add(new Transaction(
                            parts[0],
                            Double.parseDouble(parts[1]),
                            parts[2],
                            parts[3],
                            parts[4]
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        transactions.sort((t1, t2) -> {
            try {
                return LocalDate.parse(t2.getDate()).compareTo(LocalDate.parse(t1.getDate()));
            } catch (Exception e) {
                return 0;
            }
        });

        return transactions;
    }

    // ---------------- BUDGET ----------------

    public static List<BudgetCategory> loadBudgetCategories() {
        List<BudgetCategory> categories = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(BUDGET_FILE))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length == 2) {
                    categories.add(new BudgetCategory(
                            parts[0],
                            Double.parseDouble(parts[1]),
                            calculateSpent(parts[0])
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    private static double calculateSpent(String categoryName) {
        List<Transaction> transactions = loadTransactions();
        double total = 0;

        for (Transaction t : transactions) {
            if (t.getCategory().equalsIgnoreCase(categoryName)
                    && t.getType().equalsIgnoreCase("Expense")) {
                total += t.getAmount();
            }
        }

        return total;
    }

    public static void saveBudgetCategory(BudgetCategory category) {
        try (FileWriter writer = new FileWriter(BUDGET_FILE, true)) {
            writer.write(category.toCSV() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}