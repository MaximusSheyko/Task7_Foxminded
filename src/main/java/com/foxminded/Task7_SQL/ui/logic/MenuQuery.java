package com.foxminded.Task7_SQL.ui.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.service.GroupQuery;
import com.foxminded.Task7_SQL.service.menuquery.CourseQuery;
import com.foxminded.Task7_SQL.service.menuquery.StudentQuery;

import static java.lang.System.out;

public class MenuQuery {
    StudentQuery studentQuery;
    GroupQuery groupQuery;
    CourseQuery courseQuery;
    
    public MenuQuery(StudentQuery studentQuery, GroupQuery groupQuery, CourseQuery courseQuery) {
	this.studentQuery = studentQuery;
	this.groupQuery = groupQuery;
	this.courseQuery = courseQuery;
    }

    public void findAllGroupsWithLessOrEqualsStudentCount() {
	try (var scanner = new Scanner(System.in)) {
	    out.println("Enter count student : ");
	    var input = scanner.nextInt();
	 
	    courseQuery.getCountStudentAllCourses()
	    	.entrySet().stream().filter(key -> key.getValue() <= input)
	    	.forEach(students -> out.println(courseQuery.getAllCourses()
	    		.get(students.getKey() - 1).getName() + " amount students: "
	    			+ courseQuery.getCountStudentAllCourses().get(students.getKey())));
	}

    }

    public void findStudentsRelatedToCourseName() {
	try (var scanner = new Scanner(System.in)) {
	    out.print("Please, enter course name : ");
	    var courseName = scanner.next();
	    var studentsFromCourse = courseQuery.getAllStudentsOnCourses();
	    boolean courseIsFound = studentsFromCourse.containsKey(courseName);

	    if (courseIsFound) {
		studentsFromCourse.keySet().forEach(corseName -> studentsFromCourse.get(corseName).stream()
			.forEach(id -> out.println("Course: " + courseName + " - " 
			    + studentQuery.getStudentByID(id))));
	    } else {
		out.println("Course is not found");
	    }
	}
    }

    public void addStudent() {
	try (var input = new Scanner(System.in)) {
	    var idGroup = 0;
	    out.println("Please, enter student's firstname :");
	    var firstName = input.next();
	    out.println("Please, enter student's lastname :");
	    var lastName = input.next();
	    var groups = groupQuery.getAllGroups();
	    
	    groups.forEach(out::println);
	    
	    idGroup = selectGroupId(groups);
	    studentQuery.saveStudentToTable(firstName, lastName, idGroup);
	}
    }

    private int selectGroupId(List<Group> groups) {
	var idGroups = groups.stream()
		.sorted(Comparator.comparing(Group::getId))
		.map(Group::getId)
		.toList();
	var idGroup = 0;

	try (var input = new Scanner(System.in)) {
	    do {
		out.println("Please, enter group ID:");
		idGroup = input.nextInt();
	    } while (!idGroups.contains(idGroup));
	}

	return idGroup;
    }

    public void deleteStudentById() {
	var idStudents = studentQuery.getAllStudents()
		.stream().map(student -> student.getPersonalID()).toList();
	var idStudent = 0;

	try (var input = new Scanner(System.in)) {

	    do {
		out.print("Enter student's id to remove: ");
		idStudent = input.nextInt();
	    } while (!idStudents.contains(idStudent));
	}
	
	studentQuery.deleteStudentByID(idStudent);
    }

    public void addStudentToCourseFromList() {
	List<String> saveIdCourses = new ArrayList<>();
	var currentStudentId = new AtomicInteger();

	try (var scanner = new Scanner(System.in)) {
	    currentStudentId.set(selectStudentId(scanner));

	    if (currentStudentId.get() != -1) {
		saveIdCourses.addAll(selectCourses(scanner));
		saveIdCourses.forEach(id -> studentQuery
				.subscribeStudentToCourse(currentStudentId.get()
					, Integer.valueOf(id)));
	    } else {
		out.print("Student was not added");
	    }
	}
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

     public void removeStudentFromOneOfHisCourses() {
	List<String> coursesId = new ArrayList<>();
	List<String> studentsId = studentQuery.getAllStudents().stream()
		.map(student -> String.valueOf(student.getPersonalID())).toList();
	String studentId;
	String courseId;

	try (var scanner = new Scanner(System.in)) {
	    do {
		out.println("Please, select students id from 1 to " + studentsId.size() + " or enter 'q' to exit");
		studentId = scanner.next();

		if (studentId.equals("q")) {
		    break;
		}

	    } while (!studentsId.contains(studentId));

	    if (!studentId.equals("q")) {
		coursesId = courseQuery.getAllCoursesIdByStudentId(Integer.parseInt(studentId));

		do {
		    out.println("Please, select the id of the course from which"
			    + " to undsubscribe. Enter 'show' for view student courses");
		    courseId = scanner.next().toLowerCase();

		    if (courseId.equals("show")) {
			courseQuery.getAllCourses().forEach(out::println);
		    }
		} while (!coursesId.contains(courseId));

		courseQuery.unsubscribeStudentFromCourse(Integer.valueOf(studentId),
			Integer.valueOf(courseId));
	    } else {
		out.print("Student was not selected");
	    }
	}
    }
}
