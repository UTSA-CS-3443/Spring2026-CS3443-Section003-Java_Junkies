package com.budgetwise.controller;

import com.budgetwise.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML private Label totalRemainingBudget;
    @FXML private Label incomeLabel;
    @FXML private Label expensesLabel;
    @FXML private ProgressBar budgetGoalProgress;

    @FXML private TableView<Transaction> recentTransactionsTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;
    @FXML private TableColumn<Transaction, String> descriptionColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;

    @FXML
    public void initialize() {
        List<Transaction> transactions = DataManager.loadTransactions();

        double income = 0;
        double expenses = 0;

        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        // Filter only current month
        List<Transaction> filtered = transactions.stream().filter(t -> {
            try {
                LocalDate d = LocalDate.parse(t.getDate());
                return d.getMonthValue() == currentMonth && d.getYear() == currentYear;
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());

        // Calculate totals
        for (Transaction t : filtered) {
            if (t.getType().equalsIgnoreCase("Income")) {
                income += t.getAmount();
            } else {
                expenses += t.getAmount();
            }
        }

        double balance = income - expenses;

        incomeLabel.setText(String.format("$%.2f", income));
        expensesLabel.setText(String.format("$%.2f", expenses));
        totalRemainingBudget.setText(String.format("$%.2f", balance));

        double progress = (income > 0) ? expenses / income : 0;
        budgetGoalProgress.setProgress(progress);

        // Setup table columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Show only last 5 transactions (most recent)
        int start = Math.max(filtered.size() - 5, 0);
        List<Transaction> recent = filtered.subList(start, filtered.size());

        ObservableList<Transaction> data = FXCollections.observableArrayList(recent);
        recentTransactionsTable.setItems(data);
    }
}