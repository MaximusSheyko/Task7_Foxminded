package com.foxminded.Task7_SQL.service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

public class DataBaseScriptRunner {
    
    public static void createDataBase(String pathToScriptSQL, ConnectionPoolManager connectionSource) {
	try(var connection = connectionSource.getConnection()){
	    var runner = new ScriptRunner(connection);
	    
	    try(var file = new FileReader(ClassLoader.getSystemClassLoader()
	    		.getResource(pathToScriptSQL)
	    		.getFile())){
		runner.runScript(file);
	    }
	}
	catch (SQLException|IOException e) {
	    e.getStackTrace();
	}
	
    }
}
