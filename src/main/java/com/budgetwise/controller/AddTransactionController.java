package com.budgetwise.controller;

import com.budgetwise.model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddTransactionController {

    @FXML private RadioButton incomeRadioButton;
    @FXML private RadioButton expenseRadioButton;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> categoryDropdown;
    @FXML private DatePicker datePicker;
    @FXML private TextField descriptionField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label messageLabel;

    private ToggleGroup typeToggleGroup;

    @FXML
    public void initialize() {
        typeToggleGroup = new ToggleGroup();

        incomeRadioButton.setToggleGroup(typeToggleGroup);
        expenseRadioButton.setToggleGroup(typeToggleGroup);

        categoryDropdown.getItems().addAll(
                "Food",
                "Rent",
                "Transportation",
                "Entertainment",
                "Utilities",
                "School",
                "Income",
                "Other"
        );
    }

    @FXML
    private void handleSaveTransaction() {
        try {
            RadioButton selectedRadioButton = (RadioButton) typeToggleGroup.getSelectedToggle();

            if (selectedRadioButton == null) {
                messageLabel.setText("Please select Income or Expense.");
                return;
            }

            if (amountField.getText().isEmpty() ||
                    categoryDropdown.getValue() == null ||
                    datePicker.getValue() == null ||
                    descriptionField.getText().isEmpty()) {

                messageLabel.setText("Please fill out all fields.");
                return;
            }

            String type = selectedRadioButton.getText();
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryDropdown.getValue();
            String date = datePicker.getValue().toString();
            String description = descriptionField.getText();

            Transaction transaction = new Transaction(type, amount, category, date, description);

            DataManager.saveTransaction(transaction);

            messageLabel.setText("Transaction saved successfully.");

            clearForm();

        } catch (NumberFormatException e) {
            messageLabel.setText("Amount must be a valid number.");
        } catch (Exception e) {
            messageLabel.setText("Error saving transaction.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        clearForm();
        messageLabel.setText("");
    }

    private void clearForm() {
        typeToggleGroup.selectToggle(null);
        amountField.clear();
        categoryDropdown.setValue(null);
        datePicker.setValue(null);
        descriptionField.clear();
    }
}