package com.foxminded.Task7_SQL.dao.interfaces;

import java.sql.SQLException;

import com.foxminded.Task7_SQL.entity.Student;

public interface StudentQuery extends GetterByIdQuery<Student> {
    public<T> Boolean addStudentToCourseById(T idStudent, T idCourse);
    public boolean update(Student obj) throws SQLException;
    public <T> Boolean deleteById(T id);
}
