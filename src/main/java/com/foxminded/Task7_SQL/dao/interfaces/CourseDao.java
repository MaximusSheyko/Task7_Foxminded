package com.foxminded.Task7_SQL.dao.interfaces;

import java.util.List;

import com.foxminded.Task7_SQL.dao.DAOException;

public interface CourseDao<T, K> extends GenericDao<K> {
    public void deleteCourseForStudent(T studentId, T courseId) throws DAOException;

    public List<T> getAllCoursesIdByStudentId(T studentId) throws DAOException;

    public List<T> getStudensIdtOnCourseByName(String courseName) throws DAOException;

    public T countAllStudentsByCourseID(T courseId) throws DAOException;
}
