package com.unimater.dao;

import com.unimater.model.ProductType;

import java.sql.Connection;
import java.util.List;


public class ProductTypeDAO extends GenericDAOImpl<ProductType> {

    private static final String TABLE_NAME = "product_type";
    private static final List<String> COLUMNS = List.of("description");

    public ProductTypeDAO(Connection connection) {
        super(ProductType::new, connection); // Passa um Supplier para criar novas instâncias de ProductType
        super.tableName = TABLE_NAME;
        super.columns = COLUMNS;
    }
    @Override
    public ProductType findById(int id) {
        return super.getById(id); // Usa o método da classe base GenericDAOImpl
    }
}

