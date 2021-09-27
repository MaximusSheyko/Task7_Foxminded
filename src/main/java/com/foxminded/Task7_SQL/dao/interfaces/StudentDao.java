package com.foxminded.Task7_SQL.dao.interfaces;

import com.foxminded.Task7_SQL.dao.DAOException;
import com.foxminded.Task7_SQL.entity.Student;

public interface StudentDao<T, K> extends GenericDao<K> {
    public void addStudentToCourseById(T idStudent, T idCourse) throws DAOException;

    public void update(Student obj) throws DAOException;

    public void deleteById(T id) throws DAOException;

    public K getById(int id) throws DAOException;
}
