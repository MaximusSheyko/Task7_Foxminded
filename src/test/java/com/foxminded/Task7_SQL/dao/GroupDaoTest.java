package com.foxminded.Task7_SQL.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.service.ConfigDataBase;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;

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
    void testGetAllData() throws DAOException {
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
    void testGetAllData_isNoEmptyNoNull() throws DAOException {
	var group = new Group.GroupBuilder()
		.setId(1)
		.setName("MV-11")
		.build();
	Predicate<List<Group>> isListNoEmptyNoNull = t -> t.isEmpty() && t == null;

	groupJdbcDao.save(group);
	assertFalse(isListNoEmptyNoNull.test(groupJdbcDao.getAllData()));
    }

    @Test
    void testSaveGroup() throws DAOException {
	var group = new Group.GroupBuilder()
		.setName("Software_Engineering")
		.build();
	
	groupJdbcDao.save(group);
	assertTrue(groupJdbcDao.getAllData().stream()
		.anyMatch(e -> e.getName().equals(group.getName())));
    }

    @Test
    void testCountStudentInGroupById() throws DAOException {
	var amountStudentsOnGroup = 2;
	
	assertEquals(amountStudentsOnGroup, groupJdbcDao.countStudentInGroupById(1));
    }
}
