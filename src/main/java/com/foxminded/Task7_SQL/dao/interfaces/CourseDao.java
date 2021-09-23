package com.foxminded.Task7_SQL.dao.interfaces;

import java.util.List;

public interface CourseDao<T, K> extends GenericDao<K> {
    public void deleteCourseForStudent(T studentId, T courseId);
    public List<T> getAllCoursesIdByStudentId(T studentId);
    public List<T> getIdStudenstOnCourseByName(String courseName);
    public T countAllStudentsByCourseID(T courseId); 
}
