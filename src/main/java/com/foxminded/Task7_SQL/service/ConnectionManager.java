package com.foxminded.Task7_SQL.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String USER = "postgres";
    private static final String PASSWORD = "boroh";
    private static final String URL = "jdbc:postgresql://localhost:5432/testBD";
    
    private ConnectionManager() {
	
    }
    
    public static Connection open() throws SQLException{
	return DriverManager.getConnection(URL,USER, PASSWORD);
    }
}
