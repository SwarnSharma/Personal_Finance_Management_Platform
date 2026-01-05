package com.finance.model;

import java.util.Date;

public class Expense {

    private int id; // expense_id in DB
    private int userId;
    private double amount;
    private String category;
    private Date expenseDate;
    private String description;

    // Default constructor
    public Expense() {
    }

    // Constructor for new expense (DB will generate ID)
    public Expense(int userId, double amount, String category, Date expenseDate, String description) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
        this.description = description;
    }

    // Constructor with ID (existing expense from DB)
    public Expense(int id, int userId, double amount, String category, Date expenseDate, String description) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
        this.description = description;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
