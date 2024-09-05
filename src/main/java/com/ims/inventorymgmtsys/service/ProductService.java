package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.entity.ProductWrapper;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(String id);

    boolean update(Product product);

    void save(Product product);

    ProductWrapper getProductWrapper();

    boolean updateProducts(List<Product> products);

    Product findByName(String name);

}
