package com.foxminded.Task7_SQL.dao.interfaces;

import java.util.List;

import com.foxminded.Task7_SQL.dao.DAOException;

public interface GenericDao<T> {
    public List<T> getAllData() throws DAOException;

    public void save(T obj) throws DAOException;
}
