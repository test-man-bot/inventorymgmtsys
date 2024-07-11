package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/list")
    public String displayList(Model model) {
        List<Product> products = catalogService.findAll();
        model.addAttribute("productList", products);
        return "catalog/productList";
    }

    @GetMapping("details")
    public String displayDetails(@RequestParam String productId, Model model) {
        Product product = catalogService.findById(productId);
        model.addAttribute("product", product);
        return "catalog/productDetails";
    }

}
