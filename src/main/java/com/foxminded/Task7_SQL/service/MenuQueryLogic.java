package com.foxminded.Task7_SQL.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.dao.GroupDao;
import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.entity.Student;

import static java.lang.System.out;

public class MenuQueryLogic {
    GroupDao groupDao;
    StudentDao studentDao;
    CourseDao courseDao;

    public MenuQueryLogic(GroupDao groupDao, StudentDao studentDao, CourseDao courseDao) {
	this.groupDao = groupDao;
	this.studentDao = studentDao;
	this.courseDao = courseDao;
    }

    public void findAllGroupsWithLessOrEqualsStudentCount() {
	try (var scanner = new Scanner(System.in)) {
	    var groups = groupDao.getAllData();
	    var input = scanner.nextInt();
	    boolean isFound = groups.stream()
		    .anyMatch(group -> groupDao.countStudentInGroupById(group.getId()) <= input);

	    if (isFound) {
		groups.stream().filter(group -> groupDao.countStudentInGroupById(group.getId()) <= input)
			.forEach(group -> out.println(group.getName() + ", amount students : "
				+ groupDao.countStudentInGroupById(group.getId())));
	    } else {
		out.println("No groups found with that many students or less");
	    }
	}

    }

    public void findStudentsRelatedToCourseName() {
	try (var scanner = new Scanner(System.in)) {
	    out.print("Please, enter course name : ");
	    var courseName = scanner.next();
	    var studentsFromCourse = courseDao.getIdStudenstOnCourseByName(courseName);
	    boolean courseIsNoFound = studentsFromCourse.isEmpty();

	    if (!courseIsNoFound) {
		studentsFromCourse.forEach(id -> out.println(String.format("%s %s;",
			studentDao.getById(id).getFirstName(), studentDao.getById(id).getLastName())));
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
	    groupDao.getAllData().forEach(out::println);

	    idGroup = selectGroupId();

	    try {
		studentDao.save(new Student.StudentBuild().setFirstName(firstName).setLastName(lastName)
			.setGroupID(idGroup).build());
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    private int selectGroupId() {
	var idGroups = groupDao.getAllData().stream().sorted(Comparator.comparing(Group::getId)).map(Group::getId)
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
	var idStudents = studentDao.getAllData().stream().map(student -> student.getPersonalID()).toList();
	var idStudent = 0;

	try (var input = new Scanner(System.in)) {

	    do {
		out.print("Enter student's id to remove: ");
		idStudent = input.nextInt();
	    } while (!idStudents.contains(idStudent));
	}
	studentDao.deleteById(idStudent);
    }

    public void addStudentToCourseFromList() {
	List<String> saveIdCourses = new ArrayList<String>();
	var currentStudentId = new AtomicInteger();

	try (var scanner = new Scanner(System.in)) {
	    currentStudentId.set(selectStudentId(scanner));

	    if (currentStudentId.get() != -1) {
		saveIdCourses.addAll(selectCourses(scanner));
		saveIdCourses
			.forEach(id -> studentDao.addStudentToCourseById(currentStudentId.get(), Integer.valueOf(id)));
	    } else {
		out.print("Student was not added");
	    }
	}
    }

    private int selectStudentId(Scanner scanner) {
	List<String> idStudents = studentDao.getAllData().stream()
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
	List<String> idCourses = courseDao.getAllData().stream().map(course -> String.valueOf(course.getId())).toList();
	List<String> saveIdCourses = new ArrayList<>();
	String input;

	do {
	    out.println("Select course's ID from 1 to " + idCourses.size()
		    + ". Enter 'show' for return description courses");
	    input = scanner.next();

	    if (input.equals("show")) {
		courseDao.getAllData().forEach(out::println);
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
	List<String> studentsId = studentDao.getAllData().stream()
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
		coursesId = courseDao.getAllCoursesIdByStudentId(Integer.valueOf(studentId)).stream()
			.map(course -> String.valueOf(course)).toList();

		do {
		    out.println("Please, select the id of the course from which"
			    + " to undsubscribe. Enter 'show' for view student courses");
		    courseId = scanner.next().toLowerCase();

		    if (courseId.equals("show")) {
			courseDao.getAllData().forEach(out::println);
		    }
		} while (!coursesId.contains(courseId));

		courseDao.deleteCourseForStudent(Integer.valueOf(studentId), Integer.valueOf(courseId));
	    } else {
		out.print("Student was not selected");
	    }
	}
    }
}
