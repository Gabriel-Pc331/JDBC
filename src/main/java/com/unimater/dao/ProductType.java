package com.unimater.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.unimater.model.Entity;

public class ProductType implements Entity {

    private int id;
    private String description;

    public ProductType() {
        // Construtor padrão
    }

    public ProductType(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.description = rs.getString("description");
    }

    public ProductType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Entity constructFromResultSet(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.description = rs.getString("description");
        return this;
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, getDescription());
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
