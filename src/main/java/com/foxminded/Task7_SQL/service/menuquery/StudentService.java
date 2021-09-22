package com.foxminded.Task7_SQL.service.menuquery;

import java.sql.SQLException;
import java.util.List;

import com.foxminded.Task7_SQL.dao.StudentJdbcDao;
import com.foxminded.Task7_SQL.entity.Student;

public class StudentService {
    private StudentJdbcDao dao;
      
    public StudentService(StudentJdbcDao dao) {
	this.dao = dao;
    }

    public Student getStudentByID(int studentID) {
   	return dao.getById(studentID);
    }
       
    public void deleteStudentByID(int studentID) {
        dao.deleteById(studentID);
    }

    public List<Student> getAllStudents() {
	return dao.getAllData();
    }

    public void subscribeStudentToCourse(Integer studentId, Integer courseId) {
	dao.addStudentToCourseById(studentId, courseId);
    }
    
    public void saveStudentToTable(String firsName, String lastName, Integer groupID) {
	try {
	    dao.save(new Student.StudentBuild().setFirstName(firsName)
		    .setLastName(lastName)
		    .setGroupID(groupID)
		    .build());
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
}
