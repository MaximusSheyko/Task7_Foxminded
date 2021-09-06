package com.foxminded.Task7_SQL.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.ConnectionManager;

public class StudentDao extends AbstractDao<Student> {
    
    private static final String GET_BY_ID = "SELECT * FROM students "
    	+ "WHERE student_id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM students"
    	+ "WHERE student_id = ?";
    private static final String GET_ALL_STUDENTS = "SELECT * FROM students";
    private static final String SAVE_TO_TABLE = "INSERT INTO students "
    	+ "VALUES(default, ? , ?)";

    @Override
    public Student getById(int id) throws SQLException {
	Student student;
	
	try(PreparedStatement statement = ConnectionManager
		.open().prepareStatement(GET_BY_ID)){
	    statement.setInt(1, id);
	    
	    var resultSet = statement.executeQuery();
	    
	    resultSet.next();
	    student = new Student.StudentBuild()
		    .setPersonalID(resultSet.getInt("student_id"))
		    .setFirstName(resultSet.getString("firstname"))
		    .setLastName(resultSet.getString("lastname"))
		    .build();
	    resultSet.close();
	}
	
	return student;
    }

    @Override
    public void deleteById(int id) throws SQLException {
	try(PreparedStatement statement = ConnectionManager.open()
		.prepareStatement(REMOVE_BY_ID)){
	   statement.setInt(1, id);
	   statement.executeUpdate();
	}
    }

    @Override
    public List<Student> getAllData() throws SQLException {
	List<Student> students = new ArrayList<>();
	
	try(var statement = ConnectionManager.open()
		.prepareStatement(GET_ALL_STUDENTS)){
	    var resultSet = statement.executeQuery();
	    
	    while (resultSet.next()) {
		students.add
		(new Student.StudentBuild()
			.setPersonalID(resultSet.getInt(1))
			.setFirstName(resultSet.getString("firstname"))
			.setLastName(resultSet.getString("lastname"))
			.build());
	    }
	    
	    return students;
	}
    }
    
    @Override
    public void save(Student studant) throws SQLException {
	try(PreparedStatement statement = ConnectionManager.open()
		.prepareStatement(SAVE_TO_TABLE)){
	    statement.setString(1, studant.getFirstName());
	    statement.setString(2, studant.getLastName());
	    statement.executeUpdate();
	}
    }
}
