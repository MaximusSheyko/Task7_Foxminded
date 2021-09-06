package com.foxminded.Task7_SQL.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.ConnectionManager;

public class GroupDao extends AbstractDao<Group> {
    
    private static final String SAVE_TO_TABLE = "INSERT INTO groups VALUES(default, ?)"; 
    private static final String GET_ALL_GROUPS = "SELECT * FROM groups";
    
    @Override
    public Group getById(int id) throws SQLException {
	
	return null;
    }

    @Override
    public void deleteById(int id) throws SQLException {
	
    }

    @Override
    public List<Group> getAllData() throws SQLException {
	List<Group> groups = new ArrayList<>();
	
	try(var statement = ConnectionManager.open()
		.prepareStatement(GET_ALL_GROUPS)){
	    var resultSet = statement.executeQuery();
	    
	    while (resultSet.next()) {
		groups.add
		(new Group.GroupBuilder()
			.setId(resultSet.getInt(1))
			.setName(resultSet.getString("groupname"))
			.build());
	    }
	    
	    return groups;
	}
    }

    @Override
    public void save(Group group) throws SQLException {
	try(var statement = ConnectionManager.open()
		.prepareStatement(SAVE_TO_TABLE)){
	    statement.setString(1, group.getName());
	    statement.executeUpdate();
	}
    }
   
}
