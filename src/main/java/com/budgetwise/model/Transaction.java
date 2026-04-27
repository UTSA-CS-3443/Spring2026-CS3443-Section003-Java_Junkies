package com.budgetwise.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {

    private SimpleStringProperty type;
    private SimpleDoubleProperty amount;
    private SimpleStringProperty category;
    private SimpleStringProperty date;
    private SimpleStringProperty description;

    public Transaction(String type, double amount, String category, String date, String description) {
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.category = new SimpleStringProperty(category);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
    }

    public String getType() { return type.get(); }
    public double getAmount() { return amount.get(); }
    public String getCategory() { return category.get(); }
    public String getDate() { return date.get(); }
    public String getDescription() { return description.get(); }

    public String toCSV() {
        return getType() + "," + getAmount() + "," + getCategory() + "," + getDate() + "," + getDescription();
    }
}