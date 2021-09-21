package com.foxminded.Task7_SQL.dao;

import com.foxminded.Task7_SQL.service.ConnectionPoolManager;
import com.foxminded.Task7_SQL.service.DataBaseScriptRunner;

public class DataBaseTestRunner {
    public static void run(ConnectionPoolManager connectionManager) {
	DataBaseScriptRunner.createDataBase("db_setup.sql", connectionManager);
	DataBaseScriptRunner.createDataBase("upload_testbase.sql", connectionManager);
    }
}
