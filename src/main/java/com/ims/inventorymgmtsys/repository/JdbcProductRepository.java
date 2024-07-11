package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductRepository implements ProductRepository{

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public Product selectById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM t_product WHERE id = ?", new DataClassRowMapper<>(Product.class), id);
    }

    @Override
    public List<Product> selectAll() {
        return jdbcTemplate.query("SELECT * FROM t_product", new DataClassRowMapper<>(Product.class));
    }

    @Override
    public boolean update(Product product) {
        int count = jdbcTemplate.update("UPDATE t_product SET name=?, price=?, stock=? WHERE id=?",
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getId());

        if (count == 0) {
            return false;
        }
        return true;
    }


}
