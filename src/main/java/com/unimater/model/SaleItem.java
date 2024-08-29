package com.unimater.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleItem implements Entity {

    private int id;
    private Product product;
    private int quantity;
    private double percentualDiscount;
    private int saleId;

    public SaleItem() {
        // Construtor padrão
    }

    public SaleItem(int id, Product product, int quantity, double percentualDiscount, int saleId) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.percentualDiscount = percentualDiscount;
        this.saleId = saleId;
    }

    @Override
    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPercentualDiscount() {
        return percentualDiscount;
    }

    public void setPercentualDiscount(double percentualDiscount) {
        this.percentualDiscount = percentualDiscount;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    @Override
    public Entity constructFromResultSet(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        // Supondo que temos um método para obter um Product por ID
        this.product = new Product(); // Obtenha o Product correto aqui
        this.quantity = rs.getInt("quantity");
        this.percentualDiscount = rs.getDouble("percentual_discount");
        this.saleId = rs.getInt("sale_id");
        return this;
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, product.getId());
        preparedStatement.setInt(2, quantity);
        preparedStatement.setDouble(3, percentualDiscount);
        preparedStatement.setInt(4, saleId);
        return preparedStatement;
    }
}
