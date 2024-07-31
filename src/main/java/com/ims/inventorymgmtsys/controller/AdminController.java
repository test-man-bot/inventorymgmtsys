package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CatalogService catalogService;

    public AdminController(CatalogService catalogService) { this.catalogService = catalogService; }

    @GetMapping("/management")
    public String displayList(Model model) {
        List<Product> products = catalogService.findAll();
        model.addAttribute("productList", products);
        return "admin/adminManagement";
    }

}
