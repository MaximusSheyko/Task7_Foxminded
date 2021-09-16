package com.foxminded.Task7_SQL.service.menuquery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.entity.Course;

public class CourseQuery {
    CourseDao courseDao;
    
    public CourseQuery(CourseDao courseDao) {
	this.courseDao = courseDao;
    }

    public List<Course> getAllCourses() {
	return courseDao.getAllData();
    }

    public List<String> getAllCoursesIdByStudentId(Integer studentId) {
	return courseDao.getAllCoursesIdByStudentId(studentId).stream()
		.map(course -> String.valueOf(course)).toList();
    }

    public void unsubscribeStudentFromCourse(Integer studentId, Integer courseId) {
	courseDao.deleteCourseForStudent(studentId, courseId);
    }

    public Map<Integer, Integer> getCountStudentAllCourses() {
	List<Integer> coursesId = courseDao.getAllData().stream()
		.map(Course::getId).toList();
	Map<Integer, Integer> coursesIdAndCountStudent = coursesId.stream()
		.collect(Collectors.toMap(Integer::intValue, 
			value -> courseDao.countAllStudentsByStudentID(value)));

	return coursesIdAndCountStudent;
    }

    public Map<String, List<Integer>> getAllStudentsOnCourses() {
	List<Course> courses = courseDao.getAllData();
	Map<String, List<Integer>> allStudentsOnCourse = courses.stream().collect(
		Collectors.toMap(Course::getName, value -> 
		courseDao.getIdStudenstOnCourseByName(value.getName())));

	return allStudentsOnCourse;
    }
}
