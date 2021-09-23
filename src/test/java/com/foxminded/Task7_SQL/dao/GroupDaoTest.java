package com.foxminded.Task7_SQL.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.service.ConfigDataBase;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;
import com.foxminded.Task7_SQL.service.DataBaseScriptRunner;

class GroupDaoTest {
    private GroupJdbcDao groupJdbcDao;
    private ConnectionPoolManager connectionManager;
    
    @BeforeEach
    void setUp() throws Exception {
	connectionManager = new ConnectionPoolManager(ConfigDataBase.getConfig());
	groupJdbcDao = new GroupJdbcDao(connectionManager);
	DataBaseTestRunner.run(connectionManager);
    }

    @Test
    void testGetAllData() {
	var groups = Arrays.asList(new Group.GroupBuilder()
		.setId(1)
		.setName("MV-11")
		.build(),
		new Group.GroupBuilder()
		.setId(2)
		.setName("MV-12")
		.build(),
		new Group.GroupBuilder()
		.setId(3)
		.setName("MV-13")
		.build());
	
	assertEquals(groups, groupJdbcDao.getAllData());
    }

    @Test
    void testSaveGroup() {
	var group = new Group.GroupBuilder()
		.setName("Software_Engineering")
		.build();
	
	groupJdbcDao.save(group);
	assertTrue(groupJdbcDao.getAllData().stream()
		.anyMatch(e -> e.getName().equals(group.getName())));
    }

    @Test
    void testCountStudentInGroupById() {
	var amountStudentsOnGroup = 2;
	
	assertEquals(amountStudentsOnGroup, groupJdbcDao.countStudentInGroupById(1));
    }
}
