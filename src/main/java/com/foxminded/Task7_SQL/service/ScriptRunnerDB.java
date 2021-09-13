package com.foxminded.Task7_SQL.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

public class ScriptRunnerDB {
    
    public static void createDataBase(String pathToScriptSQL) {
	try(var connection = DBCPDataSource.getConnection()){
	    var runner = new ScriptRunner(connection);
	    
	    try(var file = new FileReader(pathToScriptSQL)){
		runner.runScript(file);
	    }
	}
	catch (SQLException|IOException e) {
	    e.getStackTrace();
	}
	
    }
}
