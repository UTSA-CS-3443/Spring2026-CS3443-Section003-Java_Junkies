module edu.utsa.cs3443.budgetwise {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.budgetwise to javafx.fxml;
    opens com.budgetwise.controller to javafx.fxml;

    exports com.budgetwise;
    exports com.budgetwise.model;
    exports com.budgetwise.controller;
}