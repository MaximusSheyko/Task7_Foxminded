package com.foxminded.Task7_SQL.dao.interfaces;

import java.sql.SQLException;

import com.foxminded.Task7_SQL.entity.Student;

public interface StudentDao<T, K> {
    public Boolean addStudentToCourseById(T idStudent, T idCourse);
    public boolean update(Student obj) throws SQLException;
    public Boolean deleteById(T id);
    public K getById(int id) throws SQLException;
}
