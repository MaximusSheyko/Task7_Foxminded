package com.foxminded.Task7_SQL;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.dao.GroupDao;
import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.service.GeneratorData;
import com.foxminded.Task7_SQL.service.ScriptRunnerDB;
public class Main {
   
    public static void main(String[] args) throws Exception {
	ScriptRunnerDB.createDataBase
		("resources/db_setup.sql");
	StudentDao studentDao = new StudentDao();
	
	var resultQuery = new StringBuilder();
	//final var connection = ConnectionManager.open();
	
	GeneratorData data = new GeneratorData(studentDao, new CourseDao()
		, new GroupDao());
	
	data.generateCourses();
	data.generateStudents();
	data.generateGroups();
	
		
    }	
}
