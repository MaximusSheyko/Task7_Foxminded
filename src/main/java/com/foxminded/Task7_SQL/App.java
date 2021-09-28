package com.foxminded.Task7_SQL;

import com.foxminded.Task7_SQL.dao.CourseJdbcDao;
import com.foxminded.Task7_SQL.dao.GroupJdbcDao;
import com.foxminded.Task7_SQL.dao.StudentJdbcDao;
import com.foxminded.Task7_SQL.service.ConfigDataBase;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;

public class App {
    
    public static void main(String[] args) {
	var config = ConfigDataBase.getConfig();
	
	SchoolApplication application = new SchoolApplication(
		new StudentJdbcDao(new ConnectionPoolManager(config)), 
		new CourseJdbcDao(new ConnectionPoolManager(config)), 
		new GroupJdbcDao(new ConnectionPoolManager(config)));
	
	application.run();
   }
    
}
