package com.foxminded.Task7_SQL.service.menuquery;

import java.util.ArrayList;
import java.util.List;

import com.foxminded.Task7_SQL.dao.DAOException;
import com.foxminded.Task7_SQL.dao.GroupJdbcDao;
import com.foxminded.Task7_SQL.entity.Group;

public class GroupService {
    private GroupJdbcDao dao;

    public GroupService(GroupJdbcDao dao) {
	this.dao = dao;
    }

    public List<Group> getAllGroups() {
	List<Group> groups = new ArrayList<>();

	try {
	    groups = dao.getAllData();
	} catch (DAOException e) {
	    e.printStackTrace();
	}

	return groups;
    }

    public void save(List<Group> groups) {
	groups.forEach(group -> {
	    try {
		dao.save(group);
	    } catch (DAOException e) {
		e.printStackTrace();
	    }
	});
    }
}
