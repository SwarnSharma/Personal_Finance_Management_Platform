package com.finance.test;

import java.sql.Connection;
import com.finance.db.DBConnection;

public class TestDB {

    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
    }
}
