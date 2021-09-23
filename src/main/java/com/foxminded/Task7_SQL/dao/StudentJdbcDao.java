package com.foxminded.Task7_SQL.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.Task7_SQL.dao.interfaces.GenericDao;
import com.foxminded.Task7_SQL.dao.interfaces.StudentDao;
import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;

public class StudentJdbcDao implements StudentDao<Integer, Student>{
    
    private static final String GET_BY_ID = "SELECT * FROM students "
    	+ "WHERE student_id = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM students"
    	+ " WHERE student_id = ?";
    private static final String GET_ALL_STUDENTS = "SELECT * FROM students";
    private static final String SAVE_TO_TABLE = "INSERT INTO students VALUES(default, ? , ?, ?)";
    private static final String UPDATE_STUDENT = 
	    "UPDATE students SET group_id = ?, firstname = ?, lastname = ? "
	    + "WHERE student_id = ?";
    private static final String ADD_STUDENT_TO_COURSE = 
	    "INSERT INTO students_courses VALUES(?, ?)";
    private ConnectionPoolManager connectionManager;
    
    public StudentJdbcDao(ConnectionPoolManager connectionManager) {
	this.connectionManager = connectionManager;
    }

    @Override
    public Student getById(int id) {
	var student = new Student.StudentBuild().build();
	
	try(var con = connectionManager.getConnection();
	    var statement = con.prepareStatement(GET_BY_ID)){
	    statement.setInt(1, id);
	    try(var resultSet = statement.executeQuery()){
		resultSet.next();
		student = new Student.StudentBuild()
		    .setPersonalID(resultSet.getInt("student_id"))
		    .setFirstName(resultSet.getString("firstname"))
		    .setLastName(resultSet.getString("lastname"))
		    .setGroupID(resultSet.getInt("group_id"))
		    .build();  
	    }
	} 
	catch (SQLException e) {
	    e.printStackTrace();
	}
	
	return student;
    }

    @Override
    public void deleteById(Integer id) {	
	try(var con = connectionManager.getConnection();
	    var statement = con.prepareStatement(REMOVE_BY_ID)){
	   statement.setInt(1, (int) id);
	   statement.executeUpdate();	
	}
	catch (SQLException e) {
	    e.getStackTrace();
	}
    }

    @Override
    public List<Student> getAllData() {
	List<Student> students = new ArrayList<>();
	
	try(var con = connectionManager.getConnection();
	    var statement = con.prepareStatement(GET_ALL_STUDENTS);
	    var resultSet = statement.executeQuery()){
	    
	    while (resultSet.next()) {
		students.add
		(new Student.StudentBuild()
			.setPersonalID(resultSet.getInt(1))
			.setFirstName(resultSet.getString("firstname"))
			.setLastName(resultSet.getString("lastname"))
			.build());
	    }
	}
	catch (SQLException e) {
		e.getStackTrace();
	}
	
	return students;
    }
    
    @Override
    public void save(Student studant) throws SQLException {
	try(var con = connectionManager.getConnection();
	    PreparedStatement statement = con.prepareStatement(SAVE_TO_TABLE)){
	    statement.setString(1, studant.getFirstName());
	    statement.setString(2, studant.getLastName());

	    if(studant.getGroupID() != 0) {
		statement.setInt(3, studant.getGroupID());
	    }
	    else {
		statement.setNull(3, java.sql.Types.INTEGER);
	    }
	    
	    statement.executeUpdate();
	}
    }
    
    @Override
    public void update(Student student) throws SQLException {
	try(var con = connectionManager.getConnection();
	    var statement = con.prepareStatement(UPDATE_STUDENT)){
	    statement.setInt(1, student.getGroupID());
	    statement.setString(2, student.getFirstName());
	    statement.setString(3, student.getLastName());
	    statement.setInt(4, student.getPersonalID());
	    statement.executeUpdate();
	}
    }
    
    @Override
    public void addStudentToCourseById(Integer idStudent, Integer idCourse) {
	try(var connection = connectionManager.getConnection();
	    var statement = connection.prepareStatement
		    (ADD_STUDENT_TO_COURSE);){
	    statement.setInt(1, (int) idStudent);
	    statement.setInt(2, (int) idCourse);
	    statement.executeUpdate();
	} 
	catch (SQLException e) {
	    e.getStackTrace();
	}
    }
}
