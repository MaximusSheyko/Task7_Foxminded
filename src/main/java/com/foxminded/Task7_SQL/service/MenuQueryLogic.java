package com.foxminded.Task7_SQL.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.foxminded.Task7_SQL.dao.CourseDao;
import com.foxminded.Task7_SQL.dao.GroupDao;
import com.foxminded.Task7_SQL.dao.StudentDao;
import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.entity.Student;

public class MenuQueryLogic {
    CourseDao courseDao;
    StudentDao studentDao;
    GroupDao groupDao;
    
    public MenuQueryLogic(CourseDao courseDao, StudentDao studentDao, GroupDao groupDao) {
	this.courseDao = courseDao;
	this.studentDao = studentDao;
	this.groupDao = groupDao;
    }

    public List<Course> getAllCourses() {
	return courseDao.getAllData();
    }
    
    public Student getStudentByID(int studentID) {
	return studentDao.getById(studentID);
    }
    
    public void deleteStudentByID(int studentID) {
	studentDao.deleteById(studentID);
    }
    
    public List<Group> getAllGroups(){
	return groupDao.getAllData();
    }
    
    public List<Student> getAllStudents(){
	return studentDao.getAllData();
    }
    
    public void subscribeStudentToCourse(Integer studentId, Integer courseId) {
	studentDao.addStudentToCourseById(studentId, courseId);
    }
    
    public List<String> getAllCoursesIdByStudentId(Integer studentId){
	return courseDao.getAllCoursesIdByStudentId(studentId).stream()
		.map(course -> String.valueOf(course)).toList();
    }
    
    public void unsubscribeStudentFromCourse(Integer studentId, Integer courseId) {
	courseDao.deleteCourseForStudent(studentId, courseId);
    }
    
    public void saveStudentToTable(String firsName, String lastName, Integer groupID) {
	try {
	    studentDao.save(new Student.StudentBuild().setFirstName(firsName)
	    	.setLastName(lastName).setGroupID(groupID).build());
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    public Map<Integer, Integer> getCountStudentAllCourses(){
	List<Integer> coursesId = courseDao.getAllData().stream().map(Course::getId).toList();
	Map<Integer, Integer> coursesIdAndCountStudent = coursesId.stream()
		.collect(Collectors.toMap(Integer::intValue, value ->
		groupDao.countStudentInGroupById(value)));
	
	return coursesIdAndCountStudent;
    }
    
    public Map<String, List<Integer>> getAllStudentsOnCourses(){
	List<Course> courses = courseDao.getAllData();
	List<Student> studentsId = studentDao.getAllData();
	Map<String, List<Integer>> allStudentsOnCourse = courses.stream()
		.collect(Collectors.toMap(Course::getName, 
			value -> courseDao.getIdStudenstOnCourseByName(value.getName())));
	
	return allStudentsOnCourse;
    }
    
    
}
