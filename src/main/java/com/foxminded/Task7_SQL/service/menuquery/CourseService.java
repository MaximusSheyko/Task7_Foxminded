package com.foxminded.Task7_SQL.service.menuquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.foxminded.Task7_SQL.dao.CourseJdbcDao;
import com.foxminded.Task7_SQL.dao.DAOException;
import com.foxminded.Task7_SQL.entity.Course;

public class CourseService {
    private CourseJdbcDao courseJdbcDao;

    public CourseService(CourseJdbcDao courseJdbcDao) {
	this.courseJdbcDao = courseJdbcDao;
    }

    public List<Course> getAllCourses() {
	List<Course> courses = new ArrayList<>();

	try {
	    courses = courseJdbcDao.getAllData();
	} catch (DAOException e) {
	    e.printStackTrace();
	}

	return courses;
    }

    public void save(List<Course> courses) {
	courses.forEach(course -> {
	    try {
		courseJdbcDao.save(course);
	    } catch (DAOException e) {
		e.printStackTrace();
	    }
	});
    }

    public List<String> getAllCoursesIdByStudentId(Integer studentId) {
	List<String> idCourses = new ArrayList<>();

	try {
	    idCourses = courseJdbcDao.getAllCoursesIdByStudentId(studentId).stream()
		    .map(course -> String.valueOf(course)).toList();
	} catch (DAOException e) {
	    e.printStackTrace();
	}

	return idCourses;
    }

    public void unsubscribeStudentFromCourse(Integer studentId, Integer courseId) {
	try {
	    courseJdbcDao.deleteCourseForStudent(studentId, courseId);
	} catch (DAOException e) {
	    e.printStackTrace();
	}
    }

    public Map<Integer, Integer> getCountStudentAllCourses() {
	List<Integer> coursesId = getAllCourses().stream()
		.map(Course::getId).toList();
	Map<Integer, Integer> coursesIdAndCountStudent = coursesId.stream()
		.collect(Collectors.toMap(Integer::intValue,
			value -> countStudentsOnCourseById(value)));

	return coursesIdAndCountStudent;
    }

    public Map<String, List<Integer>> getAllStudentsOnCourses() {
	Map<String, List<Integer>> allStudentsOnCourse = new HashMap<>();

	List<Course> courses = getAllCourses();
	allStudentsOnCourse = courses.stream().collect(
		Collectors.toMap(Course::getName,
			value -> countStudentsOnCourseById(value.getName())));

	return allStudentsOnCourse;
    }

    private Integer countStudentsOnCourseById(int courseId) {
	var amountStudents = 0;

	try {
	    amountStudents = courseJdbcDao.countAllStudentsByCourseID(courseId);
	} catch (DAOException e) {
	    e.printStackTrace();
	}

	return amountStudents;
    }

    private List<Integer> countStudentsOnCourseById(String name) {
	List<Integer> studentsId = new ArrayList<>();

	try {
	    studentsId = courseJdbcDao.getStudensIdtOnCourseByName(name);
	} catch (DAOException e) {
	    e.printStackTrace();
	}

	return studentsId;
    }
}
