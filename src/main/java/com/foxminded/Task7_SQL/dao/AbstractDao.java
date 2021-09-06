package com.foxminded.Task7_SQL.dao;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao<T> {
    public abstract T getById(int id) throws SQLException;
    public abstract void deleteById(int id) throws SQLException;
    public abstract List<T> getAllData() throws SQLException; 
    public abstract void save(T obj) throws SQLException;
}
