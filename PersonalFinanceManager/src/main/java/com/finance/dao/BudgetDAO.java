package com.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.finance.db.DBConnection;
import com.finance.model.Budget;

public class BudgetDAO {

    private Connection conn;

    // Default constructor for normal DAO usage
    public BudgetDAO() {
        this.conn = DBConnection.getConnection();
    }

    // Constructor used when transaction control is required
    public BudgetDAO(Connection conn) {
        this.conn = conn;
    }

    // Add a new budget for a user
    public boolean addBudget(Budget budget) {

        String sql =
                "INSERT INTO budgets (user_id, category, amount, start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, budget.getUserId());
            ps.setString(2, budget.getCategory());
            ps.setDouble(3, budget.getAmount());
            ps.setDate(4, new java.sql.Date(budget.getStartDate().getTime()));
            ps.setDate(5, new java.sql.Date(budget.getEndDate().getTime()));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all budgets belonging to a specific user
    public List<Budget> getBudgetsByUser(int userId) {

        List<Budget> list = new ArrayList<>();

        String sql =
                "SELECT * FROM budgets " +
                "WHERE user_id = ? " +
                "ORDER BY start_date DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Budget budget = new Budget(
                        rs.getInt("budget_id"),
                        rs.getInt("user_id"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                );
                list.add(budget);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Delete a budget safely using budget id and user id
    public boolean deleteBudget(int budgetId, int userId) {

        String sql =
                "DELETE FROM budgets WHERE budget_id = ? AND user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, budgetId);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Calculate total budget amount for dashboard summary
    public double getTotalBudgetByUser(int userId) {

        String sql =
                "SELECT IFNULL(SUM(amount), 0) FROM budgets WHERE user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
