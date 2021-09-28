package com.foxminded.Task7_SQL.dao.interfaces;

import com.foxminded.Task7_SQL.dao.DAOException;

public interface GroupDao<T, K> extends GenericDao<K> {
    public T countStudentInGroupById(T groupId) throws DAOException;
}
