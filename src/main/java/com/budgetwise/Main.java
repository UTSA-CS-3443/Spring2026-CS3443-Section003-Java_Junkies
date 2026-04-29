package com.budgetwise;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Change this line to load the login view first
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        // I made the window size a bit smaller here since it's just a login screen
        Scene scene = new Scene(loader.load(), 600, 400);

        stage.setTitle("BudgetWise - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}