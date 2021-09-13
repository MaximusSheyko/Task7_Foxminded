package com.foxminded.Task7_SQL.dao.interfaces;

import java.sql.SQLException;

import com.foxminded.Task7_SQL.entity.Student;

public interface StudentQuery extends GetterByIdQuery<Student> {
    public<T> void deleteById(T id);
    public<T> void addStudentToCourseById(T idStudent, T idCourse);
    public void update(Student obj) throws SQLException;
}
