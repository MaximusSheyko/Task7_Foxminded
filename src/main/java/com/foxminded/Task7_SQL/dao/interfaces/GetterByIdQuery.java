package com.foxminded.Task7_SQL.dao.interfaces;

import java.sql.SQLException;

public interface GetterByIdQuery<T> {
    public abstract T getById(int id) throws SQLException;
}
