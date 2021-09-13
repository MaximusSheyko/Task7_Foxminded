package com.foxminded.Task7_SQL.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import com.foxminded.Task7_SQL.ui.logic.MenuQueryLogic;
import com.foxminded.Task7_SQL.utils.Reader;

public class MenuChoice { 
    Reader reader;
    MenuQueryLogic menuQueryLogic;
    private static final String EXCEPTION_ISNULL = "key is null";
    private static final String MESSAGE = "Misha let's go do it again!";
    
    public MenuChoice(Reader reader, MenuQueryLogic menuQueryLogic) {
	this.reader = reader;
	this.menuQueryLogic = menuQueryLogic;
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
		menuQueryLogic.findAllGroupsWithLessOrEqualsStudentCount();
		break;
	    case "b":
	    	menuQueryLogic.findStudentsRelatedToCourseName();
		break;
	    case "c":
	    	menuQueryLogic.addStudent();
		break;
	    case "d":
	    	menuQueryLogic.deleteStudentById();
		break;
	    case "e":
	    	menuQueryLogic.addStudentToCourseFromList();
		break;
	    case "f":
	        menuQueryLogic.removeStudentFromOneOfHisCourses();
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

