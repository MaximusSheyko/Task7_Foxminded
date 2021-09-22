package com.foxminded.Task7_SQL.service;

import java.util.Random;

import com.foxminded.Task7_SQL.dao.GroupJdbcDao;
import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.service.interfaces.Generator;

public class GroupGenerator implements Generator {

    private GroupJdbcDao groupJdbcDao;

    public GroupGenerator(GroupJdbcDao groupJdbcDao) {
	this.groupJdbcDao = groupJdbcDao;
    }

    @Override
    public void generate() {
	var random = new Random();

	for (var count = 0; count < 10; count++) {
	    var groupIndex = Math.round(10 + Math.random() * 89);
	    var groupName = getRandomChar() + getRandomChar() + "-" + groupIndex;
	    groupJdbcDao.save(new Group.GroupBuilder().setName(groupName).build());
	}
    }
    
    private String getRandomChar() {
  	var random = new Random();
  	
  	return String.valueOf((char)(random.nextInt(26) + 'a')).toUpperCase();
    }
}
