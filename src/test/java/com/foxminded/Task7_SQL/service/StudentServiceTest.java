package com.foxminded.Task7_SQL.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.foxminded.Task7_SQL.dao.DAOException;
import com.foxminded.Task7_SQL.dao.StudentJdbcDao;
import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.menuquery.StudentService;

@RunWith(MockitoJUnitRunner.class)
class StudentServiceTest {

    private static final String TEST_NAME = "TEST";
    private static final int FIRST_ID = 1;
    private List<Student> students = Arrays.asList(new Student.StudentBuild()
	    .setFirstName(TEST_NAME)
	    .setLastName(TEST_NAME)
	    .setPersonalID(1)
	    .build(),
	    new Student.StudentBuild()
		    .setFirstName(TEST_NAME)
		    .setLastName("Something")
		    .setPersonalID(FIRST_ID)
		    .build());

    @Mock
    private StudentJdbcDao dao;

    @InjectMocks
    private StudentService service;

    @BeforeEach
    void setUp() throws Exception {
	dao = mock(StudentJdbcDao.class);
	service = new StudentService(dao);
    }

    @Test
    void testGetStudentByID() throws DAOException {
	service.getStudentByID(1);
	verify(dao, times(1)).getById(1);
    }

    @Test
    void testUpdate() throws DAOException {
	service.update(students);
	verify(dao, times(1)).update(students.get(0));
	verify(dao, times(1)).update(students.get(1));
    }

    @Test
    void testDeleteStudentByID() throws DAOException {
	service.deleteStudentByID(FIRST_ID);
	verify(dao, times(1)).deleteById(FIRST_ID);
	
    }

    @Test
    void testGetAllStudents() throws DAOException {
	service.getAllStudents();
	verify(dao, times(1)).getAllData();
    }
    
    @Test
    void testSubscribeStudentToCourseMapOfIntegerListOfInteger() throws DAOException {
	var testMap = new HashMap<Integer, List<Integer>>();
	
	testMap.put(FIRST_ID, Arrays.asList(FIRST_ID));
	service.subscribeStudentToCourse(testMap);
	verify(dao, times(1)).addStudentToCourseById(FIRST_ID, FIRST_ID);
    }

    @Test
    void testSaveStudentToTable() throws DAOException {
	var testGroupId = FIRST_ID;
	var student = new Student.StudentBuild()
		.setFirstName(TEST_NAME)
		.setLastName(TEST_NAME)
		.setGroupID(testGroupId)
		.build();
	
	service.saveStudentToTable(TEST_NAME, TEST_NAME, testGroupId);
	verify(dao, times(1)).save(student);
    }

    @Test
    void testSaveStudentToTableListOfStudent() throws DAOException {
	service.saveStudentToTable(students);
	verify(dao, times(1)).save(students.get(0));
	verify(dao, times(1)).save(students.get(1));
    }

    @Test
    void testSubscribeStudentToCourse() throws DAOException {
	service.subscribeStudentToCourse(FIRST_ID, FIRST_ID);
	verify(dao, times(1)).addStudentToCourseById(FIRST_ID, FIRST_ID);
    }
}
