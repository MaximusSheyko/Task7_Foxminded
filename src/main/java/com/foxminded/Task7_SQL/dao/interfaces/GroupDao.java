package com.foxminded.Task7_SQL.dao.interfaces;

public interface GroupDao<T, K> extends GenericDao<K> {
    public T countStudentInGroupById(T groupId);
}
