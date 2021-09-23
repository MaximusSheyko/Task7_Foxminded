package com.foxminded.Task7_SQL.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.service.ConfigDataBase;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;

class CourseDaoTest {
    private CourseJdbcDao courseJdbcDao;
    private static final String VALID_COURSE_NAME = "Math";
    private ConnectionPoolManager connectionManager;

    @BeforeEach
    void setUp() throws Exception {
	connectionManager = new ConnectionPoolManager(ConfigDataBase.getConfig());
	courseJdbcDao = new CourseJdbcDao(connectionManager);
	DataBaseTestRunner.run(connectionManager);
    }

    @Test
    void testGetAllData() {
	var courses = Arrays.asList(new Course.CourseBuild()
		.setName("Math")
		.setDescription("Something about the Math")
		.setId(1)
		.build(),
		new Course.CourseBuild()
			.setName("Philosophy")
			.setDescription("Something about the Philosophy")
			.setId(2)
			.build());

	assertEquals(courses, courseJdbcDao.getAllData());
    }

    @Test
    void testSaveCourse() {
	Course course = new Course.CourseBuild()
		.setName("Test")
		.setDescription("Something about")
		.build();
	Predicate<Course> isCurrentName = id -> id.getName().equals(course.getName());

	courseJdbcDao.save(course);
	assertTrue(courseJdbcDao.getAllData().stream()
		.anyMatch(e -> isCurrentName.test(e)));
    }

    @Test
    void testGetIdStudenstOnCourseByName() {
	var studentsId = Arrays.asList(1, 2);

	assertEquals(studentsId, courseJdbcDao.getIdStudenstOnCourseByName(VALID_COURSE_NAME));
    }

    @Test
    void testGetIdStudenstOnCourseByName_whenCourseNameNoFound() {
	assertTrue(courseJdbcDao.getIdStudenstOnCourseByName("Something")::isEmpty);
    }

    @Test
    void testGetAllCoursesIdByStudentId() {
	var coursesId = Arrays.asList(1, 2);

	assertEquals(coursesId, courseJdbcDao.getAllCoursesIdByStudentId(1));
    }

    @Test
    void testCountAllStudentsByStudentID() {
	var countStudents = 2;

	assertEquals(countStudents, courseJdbcDao.countAllStudentsByCourseID(1));
    }

    @Test
    void testDeleteCourseForStudent() {
	var studentId = 1;
	var courseIdToDelete = 1;
	var countCoursesBeforeDelete = 2;
	var countCoursesAfterDelete = 1;

	assertEquals(countCoursesBeforeDelete, courseJdbcDao
		.getAllCoursesIdByStudentId(studentId).size());
	courseJdbcDao.deleteCourseForStudent(studentId, courseIdToDelete);
	assertEquals(countCoursesAfterDelete, courseJdbcDao
		.getAllCoursesIdByStudentId(studentId).size());
    }
}
