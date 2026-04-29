package com.budgetwise;

import javafx.application.Application;
import javafx.stage.Stage;

// 1. You MUST import your LoginView class so Main knows where it is
import com.budgetwise.model.LoginView;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // 2. Delete the FXMLLoader code and replace it with this single line
        // This tells the application to launch your custom login screen first
        LoginView.show(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}