package com.finance.model;

import java.util.Date;

public class Budget {

    private int budgetId;
    private int userId;
    private String category;
    private double amount;
    private Date startDate;
    private Date endDate;

    // Default constructor
    public Budget() {
    }

    // Constructor with ID (DB fetch)
    public Budget(int budgetId, int userId, String category, double amount,
                  Date startDate, Date endDate) {

        this.budgetId = budgetId;
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Constructor without ID (insert)
    public Budget(int userId, String category, double amount,
                  Date startDate, Date endDate) {

        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
