package com.foxminded.Task7_SQL.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.service.ConnectionManager;

public class CourseDao extends AbstractDao<Course> {
    
    private static final String SAVE_TO_TABLE = 
	    "INSERT INTO courses (coursename, coursedescription)"
	    + " VALUES(?, ?)";

    @Override
    public Course getById(int id) throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void deleteById(int id) throws SQLException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public List<Course> getAllData() throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public void save(Course course) {
	try(PreparedStatement statement = ConnectionManager.open()
		.prepareStatement(SAVE_TO_TABLE)){
	    statement.setString(1, course.getName());
	    statement.setString(2, course.getDescription());
	    statement.executeUpdate();
	}catch (SQLException e) {
	    e.getStackTrace();
	}
    }
}
