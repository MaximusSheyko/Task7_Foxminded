package com.foxminded.Task7_SQL.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;


import com.foxminded.Task7_SQL.dao.CourseJdbcDao;
import com.foxminded.Task7_SQL.dao.GroupJdbcDao;
import com.foxminded.Task7_SQL.dao.StudentJdbcDao;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.entity.Student;

public class DataBaseGeneratorRelationship {
    private StudentJdbcDao studentJdbcDao;
    private GroupJdbcDao groupJdbcDao;
    private CourseJdbcDao courseJdbcDao;
    
    public DataBaseGeneratorRelationship(StudentJdbcDao studentJdbcDao, 
	    GroupJdbcDao groupJdbcDao, CourseJdbcDao courseJdbcDao) {
	this.studentJdbcDao = studentJdbcDao;
	this.groupJdbcDao = groupJdbcDao;
	this.courseJdbcDao = courseJdbcDao;
    }
    
    public void  assignStudentsToGroups() {
	var random = new Random();
	List<Student> students = studentJdbcDao.getAllData();
	List<Group> groups = groupJdbcDao.getAllData();
	Collections.shuffle(students);
	var idStudents = students.listIterator();
	
	for(var group : groups) {
	    var randomIteration = 10 + random.nextInt(30 - 10 + 1);
	    var count = 0;
	    
	    if (getQuntityStudentsId(idStudents) < 10){  
		break;
	    }
	    
	    while(idStudents.hasNext() && count < randomIteration) {
		Student student = idStudents.next();
		student.setGroupId(group.getId());
		
		try {
		    studentJdbcDao.update(student);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		count++;
		idStudents.remove();
	    }
	}
    }
    
    private<T> int getQuntityStudentsId(ListIterator<T> studentsId) {
	var counter = 0;
	
	while(studentsId.hasNext()) {
	    studentsId.next();
	    counter++;
	}
	
	while(studentsId.hasPrevious()) {
	    studentsId.previous();
	}
	    
	return counter;
    }
    
    public void assignCourseEachStudent()  {
	var  studentsId = studentJdbcDao.getAllData().stream()
		.map(student -> student.getPersonalID())
		.toList();
	var coursesId = courseJdbcDao.getAllData().stream()
		.map(course -> course.getId())
		.toList();
	
	studentsId.forEach(id -> assignRandomAmountCourses(id, coursesId));
    }
    
    private void assignRandomAmountCourses(int studentId, List<Integer> coursesId) {
	var random = new Random();

	Set<Integer> cache = new HashSet<>();
	int iterations = 1 + random.nextInt(3 - 1 + 1);

	for (var i = 0; i < iterations; i++) {
	    var idCourse = coursesId.get(random.nextInt(coursesId.size()));

	    if (!cache.contains(idCourse)) {
		studentJdbcDao.addStudentToCourseById(studentId, idCourse);
		cache.add(idCourse);
	    }
	}
    }
}
