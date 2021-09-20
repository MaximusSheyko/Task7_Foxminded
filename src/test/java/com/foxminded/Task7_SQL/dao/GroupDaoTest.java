package com.foxminded.Task7_SQL.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Group;
import com.foxminded.Task7_SQL.service.DataBaseScriptRunner;

class GroupDaoTest {
    private GroupDao groupDao;

    @BeforeEach
    void setUp() throws Exception {
	DataBaseScriptRunner.createDataBase("resources/db_setup.sql");
	DataBaseScriptRunner.createDataBase("src/test/java/Upload_TestBase.sql");
	groupDao = new GroupDao();
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
	
	assertEquals(groups, groupDao.getAllData());
    }

    @Test
    void testSaveGroup() {
	var group = new Group.GroupBuilder()
		.setName("Software_Engineering")
		.build();
	
	assertTrue(groupDao.save(group));
    }

    @Test
    void testCountStudentInGroupById() {
	var amountStudentsOnGroup = 2;
	
	assertEquals(amountStudentsOnGroup, groupDao.countStudentInGroupById(1));
    }
}
