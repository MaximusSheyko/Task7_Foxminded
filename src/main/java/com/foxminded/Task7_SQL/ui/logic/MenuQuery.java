package com.foxminded.Task7_SQL.ui.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.service.menuquery.CourseService;
import com.foxminded.Task7_SQL.service.menuquery.GroupService;
import com.foxminded.Task7_SQL.service.menuquery.StudentService;

import static java.lang.System.out;

public class MenuQuery {
    private StudentService studentQuery;
    private GroupService groupQuery;
    private CourseService courseQuery;
    
    public MenuQuery(StudentService studentQuery, GroupService groupQuery, CourseService courseQuery) {
	this.studentQuery = studentQuery;
	this.groupQuery = groupQuery;
	this.courseQuery = courseQuery;
    }

    public void findAllGroupsWithLessOrEqualsStudentCount(Scanner scanner) {
	out.println("Enter count student : ");
	var input = scanner.nextInt();

	courseQuery.getCountStudentAllCourses().entrySet().stream()
	.filter(key -> key.getValue() <= input)
	.forEach(students -> out.println(courseQuery.getAllCourses()
			.get(students.getKey() - 1).getName()
			+ " amount students: " + courseQuery.getCountStudentAllCourses()
			.get(students.getKey())));
    }

    public void findStudentsRelatedToCourseName(Scanner scanner) {
	out.print("Please, enter course name : ");
	var courseName = scanner.next();
	var studentsFromCourse = courseQuery.getAllStudentsOnCourses();
	boolean courseIsFound = studentsFromCourse.containsKey(courseName);

	if (courseIsFound) {
	    studentsFromCourse.keySet().forEach(corseName -> studentsFromCourse.get(corseName).stream()
		    .forEach(id -> out.println("Course: " + courseName + " - " + studentQuery
			    .getStudentByID(id))));
	} else {
	    out.println("Course is not found");
	}
    }

    public void addStudent(Scanner scanner) {
	var idGroup = 0;
	out.println("Please, enter student's firstname :");
	var firstName = scanner.next();
	out.println("Please, enter student's lastname :");
	var lastName = scanner.next();
	var groups = groupQuery.getAllGroups();

	groups.forEach(out::println);

	idGroup = selectGroupIdFromListGroups(groups, scanner);
	studentQuery.saveStudentToTable(firstName, lastName, idGroup);
    }

    public void deleteStudentById(Scanner scanner) {
	out.println("Please, select student's id to remove:");
	var idStudent = selectStudentId(scanner);
	
	if (idStudent != -1) {
	    out.println("Student has been deleted");
	    studentQuery.deleteStudentByID(idStudent);
	}else {
	    out.println("Student not selected"); 
	}
	
    }

    public void addStudentToCourseFromList(Scanner scanner) {
	List<String> saveIdCourses = new ArrayList<>();
	var currentStudentId = new AtomicInteger();

	currentStudentId.set(selectStudentId(scanner));

	if (currentStudentId.get() != -1) {
	    saveIdCourses.addAll(selectCourses(scanner));
	    saveIdCourses
		    .forEach(id -> 
		    studentQuery.subscribeStudentToCourse(currentStudentId.get()
			    , Integer.valueOf(id)));
	} else {
	    out.print("Student has not been added");
	}

    }

    public void removeStudentFromOneOfHisCourses(Scanner scanner) {
	var studentId = selectStudentId(scanner);
	
	if (studentId != -1) {
	    var courseId = selectCourseIdByStudentId(scanner, studentId);
	    courseQuery.unsubscribeStudentFromCourse(studentId, courseId);
	} else {
	    out.print("Student was not selected");
	}
    }
    
    private int selectGroupIdFromListGroups(List<Group> groups, Scanner scanner) {
	var idGroups = groups.stream()
		.sorted(Comparator.comparing(Group::getId))
		.map(Group::getId)
		.toList();
	var idGroup = 0;

	do {
	    out.println("Please, enter group ID:");
	    idGroup = scanner.nextInt();
	} while (!idGroups.contains(idGroup));
	
	return idGroup;
    }
    
    private int selectStudentId(Scanner scanner) {
	List<String> idStudents = studentQuery.getAllStudents().stream()
		.map(student -> String.valueOf(student.getPersonalID())).toList();
	String idStudent;

	do {
	    out.println("Enter student's id from 1 to " + idStudents.size() + ". Or enter 'q' to exit");
	    idStudent = scanner.next().toLowerCase();

	    if (idStudent.equals("q")) {
		return -1;
	    }
	} while (!idStudents.contains(idStudent));

	return Integer.valueOf(idStudent);
    }

    private List<String> selectCourses(Scanner scanner) {
	List<String> idCourses = courseQuery.getAllCourses().stream()
		.map(course -> String.valueOf(course.getId())).toList();
	List<String> saveIdCourses = new ArrayList<>();
	String input;

	do {
	    out.println("Select course's ID from 1 to " + idCourses.size()
		    + ". Enter 'show' for return description courses");
	    input = scanner.next();

	    if (input.equals("show")) {
		courseQuery.getAllCourses().forEach(out::println);
	    }

	    if (idCourses.contains(input)) {
		saveIdCourses.add(input);
		out.println("add other course`s ID? Enter 'y' if - yes, " + "or enter any character");
		input = scanner.next();

		if (!input.equals("y")) {
		    break;
		} else {
		    continue;
		}
	    }
	} while (!idCourses.contains(input));

	return saveIdCourses;
    }
    
    private Integer selectCourseIdByStudentId(Scanner scanner, Integer studentId) {
	var coursesId = courseQuery.getAllCoursesIdByStudentId(studentId);
	String courseId;
	
	do {
	    out.println("Please, select the id of the course from which"
		    + " to undsubscribe. Enter 'show' for view student courses");
	    courseId = scanner.next().toLowerCase();

	    if (courseId.equals("show")) {
		courseQuery.getAllCourses().forEach(out::println);
	    }
	} while (!coursesId.contains(courseId));
	
	return Integer.valueOf(courseId);
    }
}
