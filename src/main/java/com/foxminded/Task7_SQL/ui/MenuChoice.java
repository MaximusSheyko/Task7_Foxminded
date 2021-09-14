package com.foxminded.Task7_SQL.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	List<String> options = reader.read("Options");
	options.forEach(System.out::println);
    }
    
    public boolean selectChoice(String input) {
	if(input == null) {
	    throw new IllegalArgumentException(EXCEPTION_ISNULL);
	}
	
	var flag = true;

	switch(input) {
	    case "a":
		menuQuery.findAllGroupsWithLessOrEqualsStudentCount();
		break;
	    case "b":
	    	menuQuery.findStudentsRelatedToCourseName();
		break;
	    case "c":
	    	menuQuery.addStudent();
		break;
	    case "d":
	    	menuQuery.deleteStudentById();
		break;
	    case "e":
	    	menuQuery.addStudentToCourseFromList();
		break;
	    case "f":
	        menuQuery.removeStudentFromOneOfHisCourses();
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

