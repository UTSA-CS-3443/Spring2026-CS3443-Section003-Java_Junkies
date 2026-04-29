package com.budgetwise.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    protected void handleLoginAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Now we call our new method to check the CSV!
        if (validateCredentials(username, password)) {
            errorMessage.setText("Login successful! Loading dashboard...");
            loadDashboard(event);
        } else {
            errorMessage.setText("Invalid username or password.");
        }
    }

    // This method opens the CSV and looks for a match
    private boolean validateCredentials(String inputUsername, String inputPassword) {
        // This looks directly in your main project folder where your CSV is sitting
        String csvFile = "user_profile.csv";

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");

                // Username is column 0 and Password is column 1
                if (columns.length >= 2) {
                    String savedUsername = columns[0].trim();
                    String savedPassword = columns[1].trim();

                    if (savedUsername.equals(inputUsername) && savedPassword.equals(inputPassword)) {
                        return true; // We found a match!
                    }
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("Could not find or read the user_profile.csv file.");
            e.printStackTrace();
        }

        return false; // No match found or file error
    }

    private void loadDashboard(ActionEvent event) {
        try {
            // This switches the scene back to your main application view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/budgetwise/main-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load(), 1100, 700);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage.setText("Error loading dashboard.");
        }
    }
}