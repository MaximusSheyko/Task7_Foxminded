package com.foxminded.Task7_SQL.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {
    public List<T> getAllData() throws SQLException;
    public void save(T obj) throws SQLException;
}
