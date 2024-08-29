package com.unimater.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product implements Entity {

    private int id;
    private ProductType productType;
    private String description;
    private double value;

    public Product() {
        // Default constructor
    }

    public Product(int id, ProductType productType, String description, double value) {
        this.id = id;
        this.productType = productType;
        this.description = description;
        this.value = value;
    }

    @Override
    public int getId() {
        return id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getDescription() {
        return description;
    }

    public double getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Entity constructFromResultSet(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        // Assume que temos um método que obtém ProductType por ID
        this.productType = new ProductType(); // Atribua o ProductType correto aqui
        this.description = rs.getString("description");
        this.value = rs.getDouble("value");
        return this;
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, productType.getId());
        preparedStatement.setString(2, description);
        preparedStatement.setDouble(3, value);
        return preparedStatement;
    }
}
