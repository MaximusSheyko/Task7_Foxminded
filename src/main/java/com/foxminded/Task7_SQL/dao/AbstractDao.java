package com.foxminded.Task7_SQL.dao;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao<T> {
    public abstract List<T> getAllData() throws SQLException;
    public abstract Boolean save(T obj) throws SQLException;
}
