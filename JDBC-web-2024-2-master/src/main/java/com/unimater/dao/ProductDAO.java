package com.unimater.dao;

import com.unimater.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;  // Import necessário para List

public class ProductDAO extends GenericDAOImpl<Product> {

    private static final String TABLE_NAME = "product";

    public ProductDAO(Connection connection) {
        super(Product::new, connection);
        super.tableName = TABLE_NAME;
        super.columns = List.of("product_type_id", "description", "value");
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.constructFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
