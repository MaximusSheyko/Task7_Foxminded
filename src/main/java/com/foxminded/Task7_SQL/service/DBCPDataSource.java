package com.foxminded.Task7_SQL.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {
    
    private static BasicDataSource ds = new BasicDataSource();
    private static final String USER = "postgres";
    private static final String PASSWORD = "boroh";
    private static final String URL = "jdbc:postgresql://localhost:5432/testBD";
    
    static {
        ds.setUrl(URL);
        ds.setUsername(USER);
        ds.setPassword(PASSWORD);
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    private DBCPDataSource(){ 
	
    }
}
