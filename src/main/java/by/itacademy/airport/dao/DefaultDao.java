package by.itacademy.airport.dao;

import java.sql.SQLException;
import java.util.List;

public interface DefaultDao<T, I> {

    void add(T entity) throws SQLException;

    void remote(I entityId) throws SQLException;

    T getEntityById(I entityId) throws SQLException;

    List<T> getAllEntity() throws SQLException;

    void updateRecord(T entity) throws SQLException;
}
