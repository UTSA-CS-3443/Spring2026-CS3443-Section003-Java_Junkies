package com.budgetwise.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class BudgetCategory {

    private SimpleStringProperty name;
    private SimpleDoubleProperty budgetAmount;
    private SimpleDoubleProperty spentAmount;
    private SimpleDoubleProperty progress;

    public BudgetCategory(String name, double budgetAmount, double spentAmount) {
        this.name = new SimpleStringProperty(name);
        this.budgetAmount = new SimpleDoubleProperty(budgetAmount);
        this.spentAmount = new SimpleDoubleProperty(spentAmount);

        double progressValue = (budgetAmount > 0) ? spentAmount / budgetAmount : 0;
        this.progress = new SimpleDoubleProperty(progressValue);
    }

    public String getName() { return name.get(); }
    public double getBudgetAmount() { return budgetAmount.get(); }
    public double getSpentAmount() { return spentAmount.get(); }
    public double getProgress() { return progress.get(); }

    public String toCSV() {
        return getName() + "," + getBudgetAmount();
    }
}