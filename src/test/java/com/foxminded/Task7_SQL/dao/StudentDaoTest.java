package com.foxminded.Task7_SQL.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Student;
import com.foxminded.Task7_SQL.service.ConfigDataBase;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;

class StudentDaoTest {

    private StudentJdbcDao studentJdbcDao;
    private Student student;
    private final static int INVALID_ID = 4;
    private final static int VALID_ID = 1;
    private final static String TEST_NAME = "Test";
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
		.setPersonalID(VALID_ID)
		.setGroupID(VALID_ID)
		.build();

	assertEquals(student, studentJdbcDao.getById(VALID_ID));
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
			.setLastName("Teilor").setPersonalID(3)
			.build());

	assertFalse(studentJdbcDao.getAllData()::isEmpty);
	assertEquals(students, studentJdbcDao.getAllData());
    }

    @Test
    void testDeleteById_whenStudentIdIsValid() {
	Predicate<Student> isStudentId = student -> student.getPersonalID() != VALID_ID;

	studentJdbcDao.deleteById(VALID_ID);
	assertTrue(isStudentId.test(studentJdbcDao.getById(VALID_ID)));
    }

    @Test
    void testSaveStudent_whenStudentIsSaveValid() throws SQLException {
	Predicate<Student> isFirstName = student -> student.getFirstName().equals(TEST_NAME);
	Predicate<Student> isLastName = student -> student.getLastName().equals(TEST_NAME);
	student = new Student.StudentBuild()
		.setFirstName(TEST_NAME)
		.setLastName(TEST_NAME)
		.build();

	studentJdbcDao.save(student);
	assertTrue((isFirstName.and(isLastName).test(studentJdbcDao.getById(4))));
    }

    @Test
    void testUpdate_whenStudentIsValid() throws SQLException {
	student = new Student.StudentBuild()
		.setFirstName(TEST_NAME)
		.setLastName(TEST_NAME)
		.setPersonalID(VALID_ID)
		.setGroupID(VALID_ID)
		.build();

	studentJdbcDao.update(student);
	assertEquals(student, studentJdbcDao.getById(VALID_ID));
    }

    @Test
    void testUpdate_whenStudentIsNoValid() throws SQLException {
	Predicate<Student> isStudentId = student -> student.getPersonalID() != INVALID_ID;
	student = new Student.StudentBuild()
		.setFirstName("TestName")
		.setLastName("TestName")
		.setPersonalID(INVALID_ID)
		.build();
	studentJdbcDao.update(student);

	assertTrue(isStudentId.test(studentJdbcDao.getById(INVALID_ID)));
    }

    @Test
    void testAddStudentToCourseById() {
	var courseJdbcDao = new CourseJdbcDao(connectionManager);
	var courseId = 2;
	var studentId = 2;

	studentJdbcDao.addStudentToCourseById(studentId, courseId);
	assertTrue(courseJdbcDao.getAllCoursesIdByStudentId(studentId).stream()
		.anyMatch(id -> id.equals(courseId)));
    }
}
