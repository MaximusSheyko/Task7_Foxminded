package com.foxminded.Task7_SQL.service.menuquery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.foxminded.Task7_SQL.dao.CourseJdbcDao;
import com.foxminded.Task7_SQL.entity.Course;

public class CourseService {
    private CourseJdbcDao courseJdbcDao;
    
    public CourseService(CourseJdbcDao courseJdbcDao) {
	this.courseJdbcDao = courseJdbcDao;
    }

    public List<Course> getAllCourses() {
	return courseJdbcDao.getAllData();
    }

    public List<String> getAllCoursesIdByStudentId(Integer studentId) {
	return courseJdbcDao.getAllCoursesIdByStudentId(studentId).stream()
		.map(course -> String.valueOf(course)).toList();
    }

    public void unsubscribeStudentFromCourse(Integer studentId, Integer courseId) {
	courseJdbcDao.deleteCourseForStudent(studentId, courseId);
    }

    public Map<Integer, Integer> getCountStudentAllCourses() {
	List<Integer> coursesId = courseJdbcDao.getAllData().stream()
		.map(Course::getId).toList();
	Map<Integer, Integer> coursesIdAndCountStudent = coursesId.stream()
		.collect(Collectors.toMap(Integer::intValue, 
			value -> courseJdbcDao.countAllStudentsByCourseID(value)));

	return coursesIdAndCountStudent;
    }

    public Map<String, List<Integer>> getAllStudentsOnCourses() {
	List<Course> courses = courseJdbcDao.getAllData();
	Map<String, List<Integer>> allStudentsOnCourse = courses.stream().collect(
		Collectors.toMap(Course::getName, value -> 
		courseJdbcDao.getIdStudenstOnCourseByName(value.getName())));

	return allStudentsOnCourse;
    }
}
