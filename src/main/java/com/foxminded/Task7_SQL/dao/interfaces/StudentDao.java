package com.foxminded.Task7_SQL.dao.interfaces;

import java.sql.SQLException;

import com.foxminded.Task7_SQL.entity.Student;

public interface StudentDao<T, K> extends GenericDao<K>{
    public void addStudentToCourseById(T idStudent, T idCourse);
    public void update(Student obj) throws SQLException;
    public void deleteById(T id);
    public K getById(int id) throws SQLException;
}
