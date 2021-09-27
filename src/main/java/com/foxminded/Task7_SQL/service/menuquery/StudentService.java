package com.foxminded.Task7_SQL.service.menuquery;

import java.util.ArrayList;
import java.util.List;

import com.foxminded.Task7_SQL.dao.DAOException;
import com.foxminded.Task7_SQL.dao.StudentJdbcDao;
import com.foxminded.Task7_SQL.entity.Student;

public class StudentService {
    private StudentJdbcDao dao;

    public StudentService(StudentJdbcDao dao) {
	this.dao = dao;
    }

    public Student getStudentByID(int studentID) {
	var student = new Student.StudentBuild().build();

	try {
	    student = dao.getById(studentID);
	} catch (DAOException e) {
	    e.printStackTrace();
	}

	return student;
    }

    public void update(List<Student> students) {
	students.forEach(student -> {
	    try {
		dao.update(student);
	    } catch (DAOException e) {
		e.printStackTrace();
	    }
	});
    }

    public void deleteStudentByID(int studentID) {
	try {
	    dao.deleteById(studentID);
	} catch (DAOException e) {
	    e.printStackTrace();
	}
    }

    public List<Student> getAllStudents() {
	List<Student> students = new ArrayList<>();

	try {
	    students = dao.getAllData();
	} catch (DAOException e) {
	    e.printStackTrace();
	}

	return students;
    }

    public void subscribeStudentToCourse(Integer studentId, Integer courseId) {
	try {
	    dao.addStudentToCourseById(studentId, courseId);
	} catch (DAOException e) {
	    e.printStackTrace();
	}
    }

    public void saveStudentToTable(String firsName, String lastName, Integer groupID) {
	try {
	    dao.save(new Student.StudentBuild().setFirstName(firsName)
		    .setLastName(lastName)
		    .setGroupID(groupID)
		    .build());
	} catch (DAOException e) {
	    e.printStackTrace();
	}
    }

    public void saveStudentToTable(List<Student> students) {
	students.forEach(student -> {
	    try {
		dao.save(student);
	    } catch (DAOException e) {
		e.printStackTrace();
	    }
	});
    }
}
