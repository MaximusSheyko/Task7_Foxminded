package com.foxminded.Task7_SQL.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.ConfigDataBase;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;
import com.foxminded.Task7_SQL.service.DataBaseScriptRunner;

class StudentDaoTest {
    
    private StudentJdbcDao studentJdbcDao;
    private Student student;
    private final static int INVALID_ID = 4;
    private final static int VALID_ID = 1;
    private ConnectionPoolManager connectionManager;
    
    @BeforeEach
    void setUp() throws Exception {
	connectionManager = new ConnectionPoolManager(ConfigDataBase.getConfig());
	studentJdbcDao = new StudentJdbcDao(connectionManager);
	DataBaseTestRunner.run(connectionManager);
    }

    @Test
    void testGetById() {
	student = new Student.StudentBuild().setFirstName("Maxim")
		.setLastName("Sheyko")
		.setPersonalID(1)
		.build();
	
	assertEquals(student, studentJdbcDao.getById(1));
    }
    
    @DisplayName("Get all students")
    @Test
    void testGetAllData() {
	var students = Arrays.asList(new Student.StudentBuild()
		.setFirstName("Maxim")
		.setLastName("Sheyko")
		.setPersonalID(1)
		.build(), 
		new Student.StudentBuild()
		.setFirstName("Vladymir")
		.setLastName("Fisher")
		.setPersonalID(2)
		.build(),
		new Student.StudentBuild()
		.setFirstName("Karen")
		.setLastName("Teilor").
		setPersonalID(3)
		.build());
	
	assertFalse(studentJdbcDao.getAllData()::isEmpty);
	assertEquals(students, studentJdbcDao.getAllData());
    }
    
    @Test
    void testDeleteById_whenStudentIdIsValid() {
	assertTrue(studentJdbcDao.deleteById(VALID_ID));
    }
    
    @Test
    void testDeleteById_whenStudentIdIsNoValid() {
	assertTrue(!studentJdbcDao.deleteById(INVALID_ID));
    }

    @Test
    void testSaveStudent_whenStudentIsSaveValid() throws SQLException {
	student = new Student.StudentBuild()
	.setFirstName("Test")
	.setLastName("Test")
	.build();
	
	assertTrue(studentJdbcDao.save(student));
    }

    @Test
    void testUpdate_whenStudentIsValid() throws SQLException {
	student = new Student.StudentBuild()
		.setFirstName("DarkBlood")
		.setLastName("Kara")
		.setPersonalID(VALID_ID)
		.setGroupID(VALID_ID)
		.build();
	
	assertTrue(studentJdbcDao.update(student));
    }
    
    @Test
    void testUpdate_whenStudentIsNoValid() throws SQLException{
	student = new Student.StudentBuild()
		.setFirstName("TestName")
		.setLastName("TestName")
		.setPersonalID(INVALID_ID)
		.build();
	
	assertTrue(!studentJdbcDao.update(student));
    }
    
    @Test
    void testAddStudentToCourseById_whenStudentIdAndCourseIdIsValids() {
	assertTrue(studentJdbcDao.addStudentToCourseById(VALID_ID, 2));
    }
}
