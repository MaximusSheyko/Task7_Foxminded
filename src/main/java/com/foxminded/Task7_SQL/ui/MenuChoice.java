package com.foxminded.Task7_SQL.ui;

import java.util.List;
import java.util.Scanner;

import com.foxminded.Task7_SQL.ui.logic.MenuQuery;
import com.foxminded.Task7_SQL.utils.Reader;

public class MenuChoice { 
    Reader reader;
    MenuQuery menuQuery;
    private static final String EXCEPTION_ISNULL = "key is null";
    private static final String MESSAGE = "Misha let's go do it again!";
    
    public MenuChoice(Reader reader, MenuQuery menuQuery) {
	this.reader = reader;
	this.menuQuery = menuQuery;
    }
    
    public void showChoice() {
	reader.read("Options").forEach(System.out::println);
    }

    public String readChoice(Scanner scanner) {
	return scanner.next();
    }
    
    public boolean selectChoice(String input, Scanner scanner) {
	if(input == null) {
	    throw new IllegalArgumentException(EXCEPTION_ISNULL);
	}
	
	var flag = true;

	switch(input) {
	    case "a":
		menuQuery.findAllGroupsWithLessOrEqualsStudentCount(scanner);
		break;
	    case "b":
	    	menuQuery.findStudentsRelatedToCourseName(scanner);
		break;
	    case "c":
	    	menuQuery.addStudent(scanner);
		break;
	    case "d":
	    	menuQuery.deleteStudentById(scanner);
		break;
	    case "e":
	    	menuQuery.addStudentToCourseFromList(scanner);
		break;
	    case "f":
	        menuQuery.removeStudentFromOneOfHisCourses(scanner);
	    	break;
	    case "q":
	    	flag = false;
	    	break;
	    default:
	    	System.out.println(MESSAGE);
	    	break;
	}
	
	return flag;
    }
}

