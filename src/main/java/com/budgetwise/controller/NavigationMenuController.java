package com.budgetwise.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class NavigationMenuController {

    @FXML
    private BorderPane contentArea;

    @FXML
    public void initialize() {
        loadPage("dashboard.fxml");
    }

    @FXML
    private void handleDashboardButton() {
        loadPage("dashboard.fxml");
    }

    @FXML
    private void handleAddTransactionButton() {
        loadPage("add-transaction.fxml");
    }

    @FXML
    private void handleTransactionsButton() {
        loadPage("transactions.fxml");
    }

    @FXML
    private void handleBudgetSetupButton() {
        loadPage("budget-setup.fxml");
    }

    @FXML
    private void handleReportsButton() {
        loadPage("reports.fxml");
    }

    private void loadPage(String fileName) {
        try {
            URL fileUrl = getClass().getResource("/com/budgetwise/" + fileName);

            if (fileUrl == null) {
                System.out.println("Could not find FXML file: " + fileName);
                return;
            }

            Node page = FXMLLoader.load(fileUrl);
            contentArea.setCenter(page);

            System.out.println("Loaded page: " + fileName);

        } catch (IOException e) {
            System.out.println("Error loading page: " + fileName);
            e.printStackTrace();
        }
    }
}