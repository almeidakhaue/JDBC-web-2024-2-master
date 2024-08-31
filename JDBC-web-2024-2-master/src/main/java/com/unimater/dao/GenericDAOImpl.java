package com.unimater.dao;

import com.unimater.model.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class GenericDAOImpl<T extends Entity> implements GenericDAO<T> {

    protected Connection connection;
    protected String tableName;
    protected List<String> columns;
    private Supplier<T> supplier;

    public GenericDAOImpl(Supplier<T> supplier, Connection connection) {
        this.supplier = supplier;
        this.connection = connection;
    }

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                T entity = supplier.get();
                entity.constructFromResultSet(rs);
                entities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public T getById(int id) {
        T entity = null;
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    entity = supplier.get();
                    entity.constructFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void upsert(T object) {
        String sql;
        if (object.getId() == 0) {
            // Insert
            sql = "INSERT INTO " + tableName + " (" +
                    columns.stream().collect(Collectors.joining(", ")) +
                    ") VALUES (" +
                    columns.stream().map(c -> "?").collect(Collectors.joining(", ")) +
                    ")";
        } else {
            // Update
            sql = "UPDATE " + tableName + " SET " +
                    columns.stream().map(c -> c + " = ?").collect(Collectors.joining(", ")) +
                    " WHERE id = ?";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            object.prepareStatement(pstmt);
            if (object.getId() != 0) {
                pstmt.setInt(columns.size() + 1, object.getId());
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
