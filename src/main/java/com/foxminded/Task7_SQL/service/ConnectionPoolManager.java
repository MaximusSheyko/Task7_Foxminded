package com.foxminded.Task7_SQL.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPoolManager {
    
    private final BasicDataSource ds;
    private final String user;
    private final String password;
    private final String url;
    private final Properties config;
    
    public ConnectionPoolManager(Properties config) {
	this.config = config;
	this.user = config.getProperty("USER").trim();
	this.password = config.getProperty("PASSWORD").trim();
	this.url = config.getProperty("URL").trim();
	ds = new BasicDataSource();
	
	connectDataBase();
	createConnectionPool();
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    private void connectDataBase() {
	ds.setUrl(url);
	ds.setUsername(user);
	ds.setPassword(password);
    }
    
    private void createConnectionPool() {
	ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(100);
    }
}
