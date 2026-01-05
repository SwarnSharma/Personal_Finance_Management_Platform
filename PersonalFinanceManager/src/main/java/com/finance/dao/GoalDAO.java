package com.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.finance.db.DBConnection;
import com.finance.model.Goal;

public class GoalDAO {

    private Connection conn;

    // Default usage
    public GoalDAO() {
        this.conn = DBConnection.getConnection();
    }

    // Transaction usage
    public GoalDAO(Connection conn) {
        this.conn = conn;
    }

    // Add new goal
    public boolean addGoal(Goal goal) {

        String sql =
                "INSERT INTO goals (user_id, description, target_amount, saved_amount, deadline, achieved) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, goal.getUserId());
            ps.setString(2, goal.getDescription());
            ps.setDouble(3, goal.getTargetAmount());
            ps.setDouble(4, goal.getSavedAmount());
            ps.setDate(5, new java.sql.Date(goal.getDeadline().getTime()));
            ps.setBoolean(6, goal.isAchieved());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get goals by user
    public List<Goal> getGoalsByUser(int userId) {

        List<Goal> list = new ArrayList<>();

        String sql = """
                SELECT goal_id, user_id, description, target_amount,
                       saved_amount, deadline, achieved
                FROM goals
                WHERE user_id = ?
                ORDER BY deadline
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Goal goal = new Goal(
                        rs.getInt("goal_id"),
                        rs.getInt("user_id"),
                        rs.getString("description"),
                        rs.getDouble("target_amount"),
                        rs.getDate("deadline"),
                        rs.getBoolean("achieved")
                );
                goal.setSavedAmount(rs.getDouble("saved_amount"));
                list.add(goal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Mark goal achieved
    public boolean markGoalAsAchieved(int goalId, int userId) {

        String sql = """
                UPDATE goals
                SET achieved = TRUE
                WHERE goal_id = ?
                  AND user_id = ?
                  AND saved_amount >= target_amount
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, goalId);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete goal safely
    public boolean deleteGoal(int goalId, int userId) {

        String sql =
                "DELETE FROM goals WHERE goal_id = ? AND user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, goalId);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add savings with auto-achieve
    public boolean addSavings(int goalId, int userId, double amount) {

        String sql = """
                UPDATE goals
                SET saved_amount = saved_amount + ?,
                    achieved = CASE
                        WHEN (saved_amount + ?) >= target_amount THEN TRUE
                        ELSE FALSE
                    END
                WHERE goal_id = ? AND user_id = ?
                """;

        String historySql =
                "INSERT INTO goal_savings (goal_id, amount) VALUES (?, ?)";

        try {

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setDouble(1, amount);
                ps.setDouble(2, amount);
                ps.setInt(3, goalId);
                ps.setInt(4, userId);

                if (ps.executeUpdate() != 1) {
                    return false;
                }
            }

            try (PreparedStatement hps = conn.prepareStatement(historySql)) {

                hps.setInt(1, goalId);
                hps.setDouble(2, amount);
                hps.executeUpdate();
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get savings history
    public List<Double> getSavingsHistory(int goalId) {

        List<Double> list = new ArrayList<>();

        String sql =
                "SELECT amount FROM goal_savings WHERE goal_id = ? ORDER BY saved_on DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, goalId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getDouble("amount"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
