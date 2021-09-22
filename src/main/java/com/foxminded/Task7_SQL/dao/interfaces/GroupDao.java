package com.foxminded.Task7_SQL.dao.interfaces;

public interface GroupDao<T> {
    public T countStudentInGroupById(T groupId);
}
