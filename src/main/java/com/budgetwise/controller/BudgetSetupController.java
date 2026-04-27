package com.budgetwise.controller;

import com.budgetwise.model.BudgetCategory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class BudgetSetupController {

    @FXML private ComboBox<String> monthDropdown;

    @FXML private TableView<BudgetCategory> categoryTable;
    @FXML private TableColumn<BudgetCategory, String> nameColumn;
    @FXML private TableColumn<BudgetCategory, Double> budgetColumn;
    @FXML private TableColumn<BudgetCategory, Double> spentColumn;
    @FXML private TableColumn<BudgetCategory, Double> progressColumn;

    @FXML
    public void initialize() {

        monthDropdown.getItems().addAll(
                "January","February","March","April","May","June",
                "July","August","September","October","November","December"
        );

        monthDropdown.setValue("April");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("budgetAmount"));
        spentColumn.setCellValueFactory(new PropertyValueFactory<>("spentAmount"));
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));

        // 🔥 THIS LINE MAKES THE PROGRESS BAR
        progressColumn.setCellFactory(column -> new TableCell<>() {
            private final ProgressBar progressBar = new ProgressBar();

            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);

                if (empty || value == null) {
                    setGraphic(null);
                } else {
                    progressBar.setProgress(value);

                    // Optional: color change when over budget
                    if (value > 1.0) {
                        progressBar.setStyle("-fx-accent: red;");
                    } else if (value > 0.75) {
                        progressBar.setStyle("-fx-accent: orange;");
                    } else {
                        progressBar.setStyle("-fx-accent: green;");
                    }

                    setGraphic(progressBar);
                }
            }
        });

        loadTable();
    }

    private void loadTable() {
        List<BudgetCategory> categories = DataManager.loadBudgetCategories();
        categoryTable.setItems(FXCollections.observableArrayList(categories));
    }

    @FXML
    private void handleAddCategory() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Category");
        dialog.setHeaderText("Enter category name and budget (format: name,amount)");

        dialog.showAndWait().ifPresent(input -> {
            try {
                String[] parts = input.split(",");
                BudgetCategory category = new BudgetCategory(parts[0], Double.parseDouble(parts[1]), 0);
                DataManager.saveBudgetCategory(category);
                loadTable();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        });
    }

    @FXML
    private void handleSaveBudget() {
        System.out.println("Budget saved");
    }
}