package com.foxminded.Task7_SQL.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPoolManager {
    
    private static BasicDataSource ds = new BasicDataSource();
    private static final String USER = "postgres";
    private static final String PASSWORD = "boroh";
    private static final String URL = "jdbc:postgresql://localhost:5432/testBD";
    private static final String URL_TESTDB = "jdbc:h2:tcp://localhost/~/test";
    private static final String USER_TESTDB = "sa";
    private static final String PASSWOR_TESTDB = "";
    private static boolean TEST_MODE = false; 
    
    static {
	runStatusTestMode("resources/Switch_DB.properties");

	if(!TEST_MODE) {
	    runDataBase();
	}else {
	    runTestDataBase();
	}
        
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    public static void checkTestMode() {
	System.out.print(TEST_MODE);
    }
    
    private ConnectionPoolManager(){ 
	
    }
    
    private static void runStatusTestMode(String pathToConfig) {
	var properties = new Properties();
	
	try (var  file = new FileInputStream(pathToConfig)){
	    properties.load(file);
	    TEST_MODE = Boolean.valueOf(properties.getProperty("test_mode_DB"));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private static void runDataBase() {
	ds.setUrl(URL);
	ds.setUsername(USER);
	ds.setPassword(PASSWORD);
    }
    
    private static void runTestDataBase() {
	ds.setUrl(URL_TESTDB);
	ds.setUsername(USER_TESTDB);
	ds.setPassword(PASSWOR_TESTDB);
    }
}
