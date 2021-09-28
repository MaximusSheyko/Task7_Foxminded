package com.foxminded.Task7_SQL;

import static java.lang.System.out;

import java.util.Scanner;

import com.foxminded.Task7_SQL.dao.CourseJdbcDao;
import com.foxminded.Task7_SQL.dao.GroupJdbcDao;
import com.foxminded.Task7_SQL.dao.StudentJdbcDao;
import com.foxminded.Task7_SQL.generators.CourseGenerator;
import com.foxminded.Task7_SQL.generators.GroupGenerator;
import com.foxminded.Task7_SQL.generators.RelationshipGenerator;
import com.foxminded.Task7_SQL.generators.StudentGenerator;
import com.foxminded.Task7_SQL.service.ConfigDataBase;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;
import com.foxminded.Task7_SQL.service.DataBaseScriptRunner;
import com.foxminded.Task7_SQL.service.menuquery.CourseService;
import com.foxminded.Task7_SQL.service.menuquery.GroupService;
import com.foxminded.Task7_SQL.service.menuquery.StudentService;
import com.foxminded.Task7_SQL.ui.MenuChoice;
import com.foxminded.Task7_SQL.ui.logic.MenuQuery;
import com.foxminded.Task7_SQL.utils.Reader;

public class SchoolApplication {
    private StudentGenerator studentGenerator;
    private CourseGenerator courseGenerator;
    private RelationshipGenerator relationshipGenerator;
    private GroupGenerator groupGenerator;
    private Reader reader;
    private MenuChoice choiceMenu;
    private MenuQuery menuQuery;
    private StudentService studentService;
    private CourseService courseService;
    private GroupService groupService;
    private ConnectionPoolManager connectionManager;
    
    private StudentJdbcDao studentJdbcDao;
    private CourseJdbcDao courseJdbcDao;
    private GroupJdbcDao groupJdbcDao;
     
    public SchoolApplication(StudentJdbcDao studentJdbcDao, CourseJdbcDao courseJdbcDao, GroupJdbcDao groupJdbcDao) {
	connectionManager = new ConnectionPoolManager(ConfigDataBase.getConfig());
	this.studentJdbcDao = studentJdbcDao;
	this.courseJdbcDao = courseJdbcDao;
	this.groupJdbcDao = groupJdbcDao;
	courseService = new CourseService(courseJdbcDao);
	studentService = new StudentService(studentJdbcDao);
	groupService = new GroupService(groupJdbcDao);
	reader = new Reader();
	relationshipGenerator = new RelationshipGenerator();
	menuQuery = new MenuQuery(studentService, groupService, courseService);
	choiceMenu = new MenuChoice(reader, menuQuery);
	
	out.println("Welcome, step 1: Run script to create data base!");
	DataBaseScriptRunner.createDataBase("db_setup.sql", connectionManager);
	
	out.println("step 2: To generate students");
	studentGenerator = new StudentGenerator(reader);
	studentService.saveStudentToTable(studentGenerator.generate());
	
	out.println("step 3: To generate groups");
	groupGenerator = new GroupGenerator();
	groupService.save(groupGenerator.generate());
	
	out.println("step 4: To generate courses");
	courseGenerator = new CourseGenerator(reader);
	courseService.save(courseGenerator.generate());
	
	out.println("step 5: Assign each student to the group");
	var student = relationshipGenerator.getStudentWithAssignedGroups(
		studentService.getAllStudents(), groupService.getAllGroups());
	studentService.update(student);
	
	out.println("step 5: Assign each student to the course"
		+ System.lineSeparator());
	var studentsIdAndTheirCoursesId = relationshipGenerator.generateRandomCourseEachStudent(student, courseService.getAllCourses());
	studentService.subscribeStudentToCourse(studentsIdAndTheirCoursesId);
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
