package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Product;

import java.util.List;

public interface ProductRepository {

    Product selectById(String id);

    List<Product> selectAll();

    boolean update(Product product);
}
