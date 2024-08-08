package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.CartItemInput;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CatalogService {

    List<Product> findAll();

    Product findById(String id);

    CartItemInput createCartItemInput(String productId);

}
