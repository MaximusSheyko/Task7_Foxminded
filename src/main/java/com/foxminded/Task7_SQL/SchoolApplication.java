package com.foxminded.Task7_SQL;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.dao.GroupDao;
import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.service.ConfigDataBase;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;
import com.foxminded.Task7_SQL.service.CourseGenerator;
import com.foxminded.Task7_SQL.service.DataBaseGeneratorRelationship;
import com.foxminded.Task7_SQL.service.GroupGenerator;
import com.foxminded.Task7_SQL.service.DataBaseScriptRunner;
import com.foxminded.Task7_SQL.service.StudentGenerator;
import com.foxminded.Task7_SQL.service.menuquery.CourseService;
import com.foxminded.Task7_SQL.service.menuquery.GroupService;
import com.foxminded.Task7_SQL.service.menuquery.StudentService;
import com.foxminded.Task7_SQL.ui.MenuChoice;
import com.foxminded.Task7_SQL.ui.logic.MenuQuery;
import com.foxminded.Task7_SQL.utils.Reader;

import static java.lang.System.*;

import java.util.Scanner;

public class SchoolApplication {
    private StudentGenerator studentGenerator;
    private CourseGenerator courseGenerator;
    private DataBaseGeneratorRelationship dataBaseGeneratorRelationship;
    private GroupGenerator groupGenerator;
    private Reader reader;
    private MenuChoice choiceMenu;
    private MenuQuery menuQuery;
    private StudentService studentService;
    private CourseService courseService;
    private GroupService groupService;
    private ConnectionPoolManager connectionManager;
    
    private StudentDao studentDao;
    private CourseDao courseDao;
    private GroupDao groupDao;
     
    public SchoolApplication(StudentDao studentDao, CourseDao courseDao, GroupDao groupDao) {
	connectionManager = new ConnectionPoolManager(ConfigDataBase.getConfig());
	this.studentDao = studentDao;
	this.courseDao = courseDao;
	this.groupDao = groupDao;
	courseService = new CourseService(courseDao);
	studentService = new StudentService(studentDao);
	groupService = new GroupService(groupDao);
	reader = new Reader();
	menuQuery = new MenuQuery(studentService, groupService, courseService);
	choiceMenu = new MenuChoice(reader, menuQuery);
	
	out.println("Welcome, step 1: Run script to create data base!");
	DataBaseScriptRunner.createDataBase("resources/db_setup.sql", connectionManager);
	
	out.println("step 2: To generate students");
	studentGenerator = new StudentGenerator(studentDao, reader);
	studentGenerator.generate();
	
	out.println("step 3: To generate courses");
	courseGenerator = new CourseGenerator(courseDao, reader);
	courseGenerator.generate();
	
	out.println("step 4: To generate groups");
	groupGenerator = new GroupGenerator(groupDao);
	groupGenerator.generate();
	
	out.println("step 5: Assign each student to the group");
	dataBaseGeneratorRelationship = new DataBaseGeneratorRelationship
		(studentDao, groupDao, courseDao);
	dataBaseGeneratorRelationship.assignStudentsToGroups();
	
	out.println("step 6: Assign each student to the group" + System.lineSeparator());
	dataBaseGeneratorRelationship = new DataBaseGeneratorRelationship
		(studentDao, groupDao, courseDao);
	dataBaseGeneratorRelationship.assignCourseEachStudent();
    }

    public void run() {
	var flag = true;
	
	try(var scanner = new Scanner(System.in)){
	    
	    while(flag) {
		System.out.println("Choose an option to continue."
			+ " Enter 'help' to show options");
		var option = choiceMenu.readChoice(scanner).toLowerCase();
		
		if(!option.equals("help")) {
		    flag = choiceMenu.selectChoice(option, scanner);
		}else {
		    choiceMenu.showChoice();
		}
	        
	    }
	}
    }
}
