package com.budgetwise;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class LoginView {

    public static void show(Stage stage) {
        Label title = new Label("Login");

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button loginButton = new Button("Login");
        Label message = new Label();

        loginButton.setOnAction(e -> {
            if (username.getText().equals("admin") && password.getText().equals("1234")) {
                try {
                    FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/com/budgetwise/main-view.fxml"));
                    Scene scene = new Scene(loader.load(), 1100, 700);

                    stage.setTitle("BudgetWise");
                    stage.setScene(scene);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                message.setText("Invalid login");
            }
        });

        VBox layout = new VBox(10, title, username, password, loginButton, message);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}