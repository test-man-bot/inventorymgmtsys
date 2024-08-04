package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(String id);

    Boolean save(Product product);
}
