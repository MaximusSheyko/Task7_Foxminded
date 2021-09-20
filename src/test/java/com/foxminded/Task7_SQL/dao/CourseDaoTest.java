package com.foxminded.Task7_SQL.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.service.DataBaseScriptRunner;

class CourseDaoTest {
    private CourseDao courseDao;
    private static final String VALID_COURSE_NAME = "Math";
   

    @BeforeEach
    void setUp() throws Exception {
	courseDao = new CourseDao();
	DataBaseScriptRunner.createDataBase("resources/db_setup.sql");
	DataBaseScriptRunner.createDataBase("src/test/java/Upload_TestBase.sql");
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
	
	assertEquals(courses, courseDao.getAllData());
    }

    @Test
    void testSaveCourse() {
	Course course = new Course.CourseBuild()
		.setName("Test")
		.setDescription("Something about")
		.build();
	
	assertTrue(courseDao.save(course));
    }

    @Test
    void testGetIdStudenstOnCourseByName() {
	var studentsId = Arrays.asList(1,2);
	
	assertEquals(studentsId, courseDao.getIdStudenstOnCourseByName(VALID_COURSE_NAME));
    }
    
    @Test
    void testGetIdStudenstOnCourseByName_whenCourseNameNoFound() {
  	assertTrue(courseDao.getIdStudenstOnCourseByName("Something")::isEmpty);
      }

    @Test
    void testGetAllCoursesIdByStudentId() {
	var coursesId = Arrays.asList(2,1);
	
	assertEquals(coursesId, courseDao.getAllCoursesIdByStudentId(1));
    }

    @Test
    void testCountAllStudentsByStudentID() {
	var countStudents = 2;
	
	assertEquals(countStudents, courseDao.countAllStudentsByStudentID(1));
    }
    
   @Test
    void testDeleteCourseForStudent() {
       var studentId = 1;
       var courseID = 1;
     
       courseDao.deleteCourseForStudent(studentId, courseID);
       assertEquals(1, courseDao.getAllCoursesIdByStudentId(studentId).size());
    }
}
