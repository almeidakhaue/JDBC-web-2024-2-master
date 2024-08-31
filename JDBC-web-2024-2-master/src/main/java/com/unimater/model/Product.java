package com.unimater.model;

import com.unimater.dao.ProductTypeDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product implements Entity {

    private int id;
    private ProductType productType;
    private String description;
    private double value;

    public Product() {
        // Construtor padrão
    }

    public Product(int id, ProductType productType, String description, double value) {
        this.id = id;
        this.productType = productType;
        this.description = description;
        this.value = value;
    }

    @Override
    public Product constructFromResultSet(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.description = rs.getString("description");
        this.value = rs.getDouble("value");
        int productTypeId = rs.getInt("product_type_id");

        // Supondo que você tenha um ProductTypeDAO para buscar o ProductType pelo ID
        this.productType = new ProductTypeDAO(rs.getStatement().getConnection()).getById(productTypeId);

        return this; // Retorna a instância atual
    }

    @Override
    public PreparedStatement prepareStatement(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, this.productType.getId());
        pstmt.setString(2, this.description);
        pstmt.setDouble(3, this.value);
        return pstmt;
    }

    @Override
    public int getId() {
        return id;
    }

    // Getters e Setters para os outros campos
    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
