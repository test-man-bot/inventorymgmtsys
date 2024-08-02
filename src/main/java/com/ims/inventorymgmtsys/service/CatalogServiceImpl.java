package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    private final ProductRepository productRepository;

    public CatalogServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id);
    }
}
