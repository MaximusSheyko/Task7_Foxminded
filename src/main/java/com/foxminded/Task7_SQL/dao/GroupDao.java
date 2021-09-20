package com.foxminded.Task7_SQL.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.Task7_SQL.dao.interfaces.GroupQuery;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;

public class GroupDao extends AbstractDao<Group> implements GroupQuery<Integer>{
    
    private static final String SAVE_TO_TABLE = "INSERT INTO groups VALUES(default, ?)"; 
    private static final String GET_ALL_GROUPS = "SELECT * FROM groups";
    private static final String COUNT_STUDENT = "SELECT count(students.group_id) AS amount "
    	+ "FROM students WHERE students.group_id = ?";

    @Override
    public List<Group> getAllData() {
	List<Group> groups = new ArrayList<>();
	
	try(var con = ConnectionPoolManager.getConnection();
	    var statement = con.prepareStatement(GET_ALL_GROUPS);
	    var resultSet = statement.executeQuery()){
	    
	    while (resultSet.next()) {
		groups.add
		(new Group.GroupBuilder()
			.setId(resultSet.getInt(1))
			.setName(resultSet.getString("groupname"))
			.build());
	    }
	    
	    return groups;
	}catch (SQLException e) {
	    e.getStackTrace();
	}
	
	return groups;
    }

    @Override
    public Boolean save(Group group) {
	var groupIsSave = true;
	
	try(var con = ConnectionPoolManager.getConnection();
	    var statement = con.prepareStatement(SAVE_TO_TABLE)){
	    statement.setString(1, group.getName());
	    groupIsSave = statement.executeUpdate() != 1 ? false : true;
	}
	catch (Exception e) {
	    e.getStackTrace();
	}
	
	return groupIsSave;
    }
    
    @Override
    public Integer countStudentInGroupById(Integer groupId) {
	try(var con = ConnectionPoolManager.getConnection();
	    var statement = con.prepareStatement(COUNT_STUDENT)){
	    statement.setInt(1, groupId);
	    
	    try(var resultSer = statement.executeQuery()){
		if (resultSer.next()) {
		    return resultSer.getInt("amount");
		}else {
		    groupId = 0;
		}
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	return groupId;
    }
}
