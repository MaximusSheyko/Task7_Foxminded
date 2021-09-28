package com.foxminded.Task7_SQL.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.Task7_SQL.dao.interfaces.CourseDao;
import com.foxminded.Task7_SQL.entity.Course;
import com.foxminded.Task7_SQL.service.ConnectionPoolManager;

public class CourseJdbcDao implements CourseDao<Integer, Course> {

    private static final String SAVE_TO_TABLE = "INSERT INTO courses "
	    + "(coursename, coursedescription) VALUES(?, ?)";
    private static final String GET_ALL_GROUPS = "SELECT * FROM courses";
    private static final String GET_ID_STUDENTS_ON_GROUP = "SELECT students_courses.student_id"
	    + " AS id_student FROM students_courses, courses "
	    + "WHERE courses.course_id = students_courses.course_id"
	    + " AND courses.coursename LIKE ?";
    private static final String GET_ALL_COURSES = "SELECT course_id "
	    + " FROM students_courses WHERE student_id = ?";
    private static final String REMOVE_COURSE_FROM_STUDENT = "DELETE FROM students_courses"
	    + " WHERE student_id = ?"
	    + " AND course_id = ?";
    private static final String ALL_STUDENTS_ON_COURSE = "SELECT count(student_id)"
	    + " AS totalStudents"
	    + " FROM students_courses WHERE course_id = ?;";
    private ConnectionPoolManager connectionManager;

    public CourseJdbcDao(ConnectionPoolManager connectionManager) {
	this.connectionManager = connectionManager;
    }

    @Override
    public List<Course> getAllData() throws DAOException {
	ArrayList<Course> courses = new ArrayList<>();

	try (var con = connectionManager.getConnection();
		var statement = con.prepareStatement(GET_ALL_GROUPS);
		var resultSet = statement.executeQuery()) {

	    while (resultSet.next()) {
		courses.add(new Course.CourseBuild().setId(resultSet.getInt(1))
			.setName(resultSet.getString(2))
			.setDescription(resultSet.getString(3))
			.build());
	    }
	} catch (SQLException e) {
	    throw new DAOException("Fails to get studetns from data base", e);
	}

	return courses;
    }

    @Override
    public void save(Course course) throws DAOException {
	try (var con = connectionManager.getConnection();
		var statement = con.prepareStatement(SAVE_TO_TABLE)) {
	    statement.setString(1, course.getName());
	    statement.setString(2, course.getDescription());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException("Failed to enroll student", e);
	}
    }

    @Override
    public List<Integer> getStudensIdtOnCourseByName(String courseName) throws DAOException {
	List<Integer> idStudents = new ArrayList<>();

	try (var connection = connectionManager.getConnection();
		var statement = connection.prepareStatement(GET_ID_STUDENTS_ON_GROUP)) {
	    statement.setString(1, courseName);
	    var resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		idStudents.add(resultSet.getInt("id_student"));
	    }
	} catch (SQLException e) {
	    throw new DAOException("Failed get id students by course name", e);
	}

	return idStudents;
    }

    @Override
    public List<Integer> getAllCoursesIdByStudentId(Integer studentId) throws DAOException {
	List<Integer> coursesId = new ArrayList<>();

	try (var connection = connectionManager.getConnection();
		var statement = connection.prepareStatement(GET_ALL_COURSES)) {
	    statement.setInt(1, studentId);

	    try (var resultSet = statement.executeQuery()) {

		while (resultSet.next()) {
		    coursesId.add(resultSet.getInt("course_id"));
		}
	    }
	} catch (SQLException e) {
	    throw new DAOException("Failed get id courses by student id", e);
	}

	return coursesId;
    }

    @Override
    public void deleteCourseForStudent(Integer studentId, Integer courseId) throws DAOException {
	try (var connection = connectionManager.getConnection();
		var statement = connection.prepareStatement(REMOVE_COURSE_FROM_STUDENT)) {
	    statement.setInt(1, studentId);
	    statement.setInt(2, courseId);
	    statement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException("Failed deleted course", e);
	}
    }

    @Override
    public Integer countAllStudentsByCourseID(Integer courseId) throws DAOException {
	var amountStudents = 0;

	try (var connection = connectionManager.getConnection();
		var statement = connection.prepareStatement(ALL_STUDENTS_ON_COURSE)) {
	    statement.setInt(1, courseId);

	    try (var resultSet = statement.executeQuery()) {
		if (resultSet.next()) {
		    amountStudents = resultSet.getInt(1);
		}
	    }
	} catch (SQLException e) {
	    throw new DAOException("Failed count students", e);
	}

	return amountStudents;
    }
}
