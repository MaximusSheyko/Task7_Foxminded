package com.foxminded.Task7_SQL.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.service.ConfigDataBase;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;
import com.foxminded.Task7_SQL.service.DataBaseScriptRunner;

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
	
	assertTrue(courseJdbcDao.save(course));
    }

    @Test
    void testGetIdStudenstOnCourseByName() {
	var studentsId = Arrays.asList(1,2);
	
	assertEquals(studentsId, courseJdbcDao.getIdStudenstOnCourseByName(VALID_COURSE_NAME));
    }
    
    @Test
    void testGetIdStudenstOnCourseByName_whenCourseNameNoFound() {
  	assertTrue(courseJdbcDao.getIdStudenstOnCourseByName("Something")::isEmpty);
      }

    @Test
    void testGetAllCoursesIdByStudentId() {
	var coursesId = Arrays.asList(2,1);
	
	assertEquals(coursesId, courseJdbcDao.getAllCoursesIdByStudentId(1));
    }

    @Test
    void testCountAllStudentsByStudentID() {
	var countStudents = 2;
	
	assertEquals(countStudents, courseJdbcDao.countAllStudentsByStudentID(1));
    }
    
   @Test
    void testDeleteCourseForStudent() {
       var studentId = 1;
       var courseID = 1;
     
       courseJdbcDao.deleteCourseForStudent(studentId, courseID);
       assertEquals(1, courseJdbcDao.getAllCoursesIdByStudentId(studentId).size());
    }
}
