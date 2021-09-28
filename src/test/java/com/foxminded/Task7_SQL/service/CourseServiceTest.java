package com.foxminded.Task7_SQL.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.foxminded.Task7_SQL.dao.CourseJdbcDao;
import com.foxminded.Task7_SQL.dao.DAOException;
import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.service.menuquery.CourseService;

@RunWith(MockitoJUnitRunner.class)
class CourseServiceTest {

    private static final int FIRST_ID = 1;
    private List<Course> courses = Arrays.asList(new Course.CourseBuild()
	    .setName("Test")
	    .setDescription("Test")
	    .setId(1)
	    .build());

    @Mock
    private CourseJdbcDao dao;

    @InjectMocks
    private CourseService service;

    @BeforeEach
    void setUp() throws Exception {
	dao = mock(CourseJdbcDao.class);
	service = new CourseService(dao);
    }

    @Test
    void testGetAllCourses() throws DAOException {
	service.getAllCourses();
	verify(dao, times(1)).getAllData();
    }

    @Test
    void testSave() throws DAOException {
	service.save(Arrays.asList(courses.get(0)));
	verify(dao, times(1)).save(courses.get(0));
    }

    @Test
    void testGetAllCoursesIdByStudentId() throws DAOException {
	service.getAllCoursesIdByStudentId(FIRST_ID);
	verify(dao, times(1)).getAllCoursesIdByStudentId(FIRST_ID);
    }
    
    @Test
    void testUnsubscribeStudentFromCourse() throws DAOException {
	service.unsubscribeStudentFromCourse(FIRST_ID, FIRST_ID);
	verify(dao, times(1)).deleteCourseForStudent(FIRST_ID, FIRST_ID);
    }

    @Test
    void testGetCountStudentAllCourses() throws DAOException {
	when(dao.getAllData()).thenReturn(courses);
	service.getCountStudentAllCourses();
	verify(dao, times(1)).getAllData();
	verify(dao, times(1)).countAllStudentsByCourseID(FIRST_ID);
    }

    @Test
    void testGetAllStudentsOnCourses() throws DAOException {
	when(dao.getAllData()).thenReturn(courses);
	service.getAllStudentsOnCourses();
	verify(dao, times(1)).getAllData();
	verify(dao, times(1)).getStudensIdtOnCourseByName(courses.get(0).getName());
    }
}
