package com.finance.model;

import java.util.Date;

public class Goal {

    private int goalId;
    private int userId;
    private String description;
    private double targetAmount;
    private Date deadline;
    private boolean achieved;
    private double savedAmount; // current saved amount

    // Default constructor
    public Goal() {
    }

    // Constructor with ID (existing goal from DB)
    public Goal(int goalId, int userId, String description, double targetAmount, Date deadline, boolean achieved) {
        this.goalId = goalId;
        this.userId = userId;
        this.description = description;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.achieved = achieved;
    }

    // Constructor for new goal (DB will generate ID)
    public Goal(int userId, String description, double targetAmount, Date deadline, boolean achieved) {
        this.userId = userId;
        this.description = description;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.achieved = achieved;
    }

    // Getters and setters
    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public double getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(double savedAmount) {
        this.savedAmount = savedAmount;
    }
}
