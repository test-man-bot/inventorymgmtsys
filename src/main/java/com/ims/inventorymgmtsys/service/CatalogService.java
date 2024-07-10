package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Product;

import java.util.List;

public interface CatalogService {

    List<Product> findAll();

    Product findById(String id);

}
