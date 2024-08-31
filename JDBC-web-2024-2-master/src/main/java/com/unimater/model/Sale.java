package com.unimater.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Sale implements Entity {

    private int id;
    private List<SaleItem> saleItems;
    private Timestamp insertAt;

    // Construtor sem argumentos
    public Sale() {}

    // Construtor com argumentos
    public Sale(int id, List<SaleItem> saleItems, Timestamp insertAt) {
        this.id = id;
        this.saleItems = saleItems;
        this.insertAt = insertAt;
    }

    // Implementação do método da interface Entity
    @Override
    public Sale constructFromResultSet(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.insertAt = rs.getTimestamp("insert_at");
        // Você pode adicionar lógica para carregar os SaleItems separadamente, se necessário.
        return this;
    }

    // Implementação do método da interface Entity
    @Override
    public PreparedStatement prepareStatement(PreparedStatement pstmt) throws SQLException {
        pstmt.setTimestamp(1, this.insertAt);
        
        return pstmt;
    }

    @Override
    public int getId() {
        return id;
    }

    // Getters e Setters
    public void setId(int id) {
        this.id = id;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }

    public Timestamp getInsertAt() {
        return insertAt;
    }

    public void setInsertAt(Timestamp insertAt) {
        this.insertAt = insertAt;
    }
}
