package com.unimater.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Sale implements Entity {

    private int id;
    private Timestamp insertAt;

    public Sale() {
        // Construtor padrão
    }

    public Sale(int id, Timestamp insertAt) {
        this.id = id;
        this.insertAt = insertAt;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getInsertAt() {
        return insertAt;
    }

    public void setInsertAt(Timestamp insertAt) {
        this.insertAt = insertAt;
    }

    @Override
    public Entity constructFromResultSet(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.insertAt = rs.getTimestamp("insert_at");
        return this;
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setTimestamp(1, insertAt);
        return preparedStatement;
    }
}
