package com.foxminded.Task7_SQL;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.dao.GroupDao;
import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.service.CourseGenerator;
import com.foxminded.Task7_SQL.service.GeneratorRelationshipDB;
import com.foxminded.Task7_SQL.service.GroupGenerator;
import com.foxminded.Task7_SQL.service.ScriptRunnerDB;
import com.foxminded.Task7_SQL.service.StudentGenerator;
import com.foxminded.Task7_SQL.ui.MenuChoice;
import com.foxminded.Task7_SQL.ui.logic.MenuQueryLogic;
import com.foxminded.Task7_SQL.utils.Reader;

import static java.lang.System.*;

public class SchoolApplication {
    StudentGenerator studentGenerator;
    CourseGenerator courseGenerator;
    GeneratorRelationshipDB generatorRelationshipDB;
    GroupGenerator groupGenerator;
    Reader reader;
    MenuChoice choiceMenu;
    MenuQueryLogic menuQueryLogic;
    
    StudentDao studentDao;
    CourseDao courseDao;
    GroupDao groupDao;
     
    public SchoolApplication(StudentDao studentDao, CourseDao courseDao, GroupDao groupDao) {
	this.studentDao = studentDao;
	this.courseDao = courseDao;
	this.groupDao = groupDao;
	reader = new Reader();
	menuQueryLogic = new MenuQueryLogic(groupDao, studentDao, courseDao);
	choiceMenu = new MenuChoice(reader, menuQueryLogic);
	
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
