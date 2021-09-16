package com.foxminded.Task7_SQL;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.dao.GroupDao;
import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.service.CourseGenerator;
import com.foxminded.Task7_SQL.service.GeneratorRelationshipDB;
import com.foxminded.Task7_SQL.service.GroupGenerator;
import com.foxminded.Task7_SQL.service.GroupQuery;
import com.foxminded.Task7_SQL.service.ScriptRunnerDB;
import com.foxminded.Task7_SQL.service.menuquery.CourseQuery;
import com.foxminded.Task7_SQL.service.menuquery.StudentGenerator;
import com.foxminded.Task7_SQL.service.menuquery.StudentQuery;
import com.foxminded.Task7_SQL.ui.MenuChoice;
import com.foxminded.Task7_SQL.ui.logic.MenuQuery;
import com.foxminded.Task7_SQL.utils.Reader;

import static java.lang.System.*;

public class SchoolApplication {
    StudentGenerator studentGenerator;
    CourseGenerator courseGenerator;
    GeneratorRelationshipDB generatorRelationshipDB;
    GroupGenerator groupGenerator;
    Reader reader;
    MenuChoice choiceMenu;
    MenuQuery menuQuery;
    StudentQuery studentQuery;
    CourseQuery courseQuery;
    GroupQuery groupQuery;
    
    StudentDao studentDao;
    CourseDao courseDao;
    GroupDao groupDao;
     
    public SchoolApplication(StudentDao studentDao, CourseDao courseDao, GroupDao groupDao) {
	this.studentDao = studentDao;
	this.courseDao = courseDao;
	this.groupDao = groupDao;
	courseQuery = new CourseQuery(courseDao);
	studentQuery = new StudentQuery(studentDao);
	groupQuery = new GroupQuery(groupDao);
	reader = new Reader();
	menuQuery = new MenuQuery(studentQuery, groupQuery, courseQuery);
	choiceMenu = new MenuChoice(reader, menuQuery);
	
	out.println("Welcome, step 1: Run script to create data base!");
	ScriptRunnerDB.createDataBase("resources/db_setup.sql");
	
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
	generatorRelationshipDB = new GeneratorRelationshipDB
		(studentDao, groupDao, courseDao);
	generatorRelationshipDB.assignStudentsToGroups();
	
	out.println("step 6: Assign each student to the group" + System.lineSeparator());
	generatorRelationshipDB = new GeneratorRelationshipDB
		(studentDao, groupDao, courseDao);
	generatorRelationshipDB.assignCourseEachStudent();
    }

    public void run() {
	var flag = true;
	choiceMenu.showChoice();
	
	while(flag) {
	   flag = choiceMenu.selectChoice("a");
	}
    }
}
