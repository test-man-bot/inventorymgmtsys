package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.Product;

import java.util.List;

public interface ProductRepository {

    Product findById(String id);

    List<Product> findAll();

    boolean save(Product product);
}
