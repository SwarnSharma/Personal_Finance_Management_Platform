package com.finance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.finance.db.DBConnection;
import com.finance.model.Expense;

public class ExpenseDAO {

    private Connection conn;

    // Default constructor for normal DAO usage
    public ExpenseDAO() {
        this.conn = DBConnection.getConnection();
    }

    // Constructor used when transaction control is required
    public ExpenseDAO(Connection conn) {
        this.conn = conn;
    }

    // Add a new expense for a user
    public boolean addExpense(Expense expense) {

        String sql =
                "INSERT INTO expenses (user_id, amount, category, expense_date, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, expense.getUserId());
            ps.setDouble(2, expense.getAmount());
            ps.setString(3, expense.getCategory());
            ps.setDate(4, new java.sql.Date(expense.getExpenseDate().getTime()));
            ps.setString(5, expense.getDescription());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all expenses belonging to a specific user
    public List<Expense> getExpensesByUser(int userId) {

        List<Expense> list = new ArrayList<>();

        String sql =
                "SELECT * FROM expenses " +
                "WHERE user_id = ? " +
                "ORDER BY expense_date DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("expense_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getDate("expense_date"),
                        rs.getString("description")
                );
                list.add(expense);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Delete an expense safely using expense id and user id
    public boolean deleteExpense(int expenseId, int userId) {

        String sql =
                "DELETE FROM expenses WHERE expense_id = ? AND user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, expenseId);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Calculate total expenses for dashboard summary
    public double getTotalExpensesByUser(int userId) {

        String sql =
                "SELECT IFNULL(SUM(amount), 0) FROM expenses WHERE user_id = ?";

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
