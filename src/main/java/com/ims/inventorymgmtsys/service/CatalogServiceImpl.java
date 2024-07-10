package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CatalogServiceImpl implements CatalogService{

    private final ProductRepository productRepository;

    public CatalogServiceImpl(ProductRepository productRepository) { this.productRepository = productRepository; }

    @Override
    public List<Product> findAll() {return productRepository.selectAll();}

    @Override
    public Product selectById(String id) {productRepository.selectById(id);}
}
