package com.foxminded.Task7_SQL.service.menuquery;

import java.util.List;

import com.foxminded.Task7_SQL.dao.GroupJdbcDao;
import com.foxminded.Task7_SQL.entity.Group;

public class GroupService {
    private GroupJdbcDao dao;
    
    public GroupService(GroupJdbcDao dao) {
	this.dao = dao;
    }

    public List<Group> getAllGroups() {
	return dao.getAllData();
    }
}
