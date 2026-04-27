package com.budgetwise.controller;

import com.budgetwise.model.Transaction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsController {

    @FXML private ComboBox<String> monthDropdown;
    @FXML private PieChart expensePieChart;
    @FXML private BarChart<String, Number> incomeVsExpenseBarChart;
    @FXML private Label categoryBreakdownLabels;

    @FXML
    public void initialize() {
        monthDropdown.getItems().addAll(
                "All Months", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        monthDropdown.setValue("All Months");
        generateCharts();
    }

    @FXML
    private void handleMonthSelection() {
        generateCharts();
    }

    private void generateCharts() {
        List<Transaction> transactions = DataManager.loadTransactions();

        double totalIncome = 0;
        double totalExpenses = 0;
        Map<String, Double> categoryTotals = new HashMap<>();

        String selectedMonth = monthDropdown.getValue();

        for (Transaction transaction : transactions) {

            if (!matchesSelectedMonth(transaction, selectedMonth)) {
                continue;
            }

            if (transaction.getType().equalsIgnoreCase("Income")) {
                totalIncome += transaction.getAmount();
            } else if (transaction.getType().equalsIgnoreCase("Expense")) {
                totalExpenses += transaction.getAmount();

                categoryTotals.put(
                        transaction.getCategory(),
                        categoryTotals.getOrDefault(transaction.getCategory(), 0.0) + transaction.getAmount()
                );
            }
        }

        expensePieChart.setData(FXCollections.observableArrayList());

        for (String category : categoryTotals.keySet()) {
            expensePieChart.getData().add(
                    new PieChart.Data(
                            category + " $" + String.format("%.2f", categoryTotals.get(category)),
                            categoryTotals.get(category)
                    )
            );
        }

        incomeVsExpenseBarChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Amount");
        series.getData().add(new XYChart.Data<>("Income", totalIncome));
        series.getData().add(new XYChart.Data<>("Expenses", totalExpenses));

        incomeVsExpenseBarChart.getData().add(series);

        categoryBreakdownLabels.setText(
                "Month: " + selectedMonth
                        + "   |   Total Income: $" + String.format("%.2f", totalIncome)
                        + "   |   Total Expenses: $" + String.format("%.2f", totalExpenses)
                        + "   |   Balance: $" + String.format("%.2f", totalIncome - totalExpenses)
        );
    }

    private boolean matchesSelectedMonth(Transaction transaction, String selectedMonth) {
        if (selectedMonth == null || selectedMonth.equals("All Months")) {
            return true;
        }

        try {
            LocalDate transactionDate = LocalDate.parse(transaction.getDate());
            Month transactionMonth = transactionDate.getMonth();

            return transactionMonth.toString().equalsIgnoreCase(selectedMonth);

        } catch (Exception e) {
            return false;
        }
    }
}