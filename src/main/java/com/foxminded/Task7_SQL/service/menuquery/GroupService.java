package com.foxminded.Task7_SQL.service.menuquery;

import java.util.List;

import com.foxminded.Task7_SQL.dao.GroupDao;
import com.foxminded.Task7_SQL.entity.Group;

public class GroupService {
    private GroupDao dao;
    
    public GroupService(GroupDao dao) {
	this.dao = dao;
    }

    public List<Group> getAllGroups() {
	return dao.getAllData();
    }
}
