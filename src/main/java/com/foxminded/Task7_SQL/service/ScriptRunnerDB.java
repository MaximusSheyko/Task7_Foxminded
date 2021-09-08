package com.foxminded.Task7_SQL.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

public class ScriptRunnerDB {
    public static void createDataBase(String pathToScriptSQL) throws SQLException, FileNotFoundException  {
	var connection = DBCPDataSource.getConnection();
	var runner = new ScriptRunner(connection);
	Reader file = new FileReader(pathToScriptSQL);

	runner.runScript(file);
	connection.close();
    }
}
