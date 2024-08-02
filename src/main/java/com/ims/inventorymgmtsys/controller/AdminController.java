package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.service.CatalogService;
import com.ims.inventorymgmtsys.service.OrderService;
import com.ims.inventorymgmtsys.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;
    private final CatalogService catalogService;

    private final ProductService productService;

    public AdminController(CatalogService catalogService, OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.catalogService = catalogService;
        this.productService = productService;
    }

    @GetMapping("/management")
    public String displayList(Model model) {
        List<Order> orders = orderService.findAll();
        List<Product> products = catalogService.findAll();
        model.addAttribute("orderList", orders);
        model.addAttribute("productList", products);
        return "admin/adminManagement";
    }

    @PostMapping("update-stock")
    public String updateStock(@RequestParam("id") String id, @RequestParam("stock") int stock, Model model) {
        try {
            Product product = productService.findById(id);

            if (product != null) {
                product.setStock(stock);
                boolean isUpdate = productService.update(product);

                model.addAttribute("updateSuccess", isUpdate);
            } else {
                model.addAttribute("updateSuccess", false);
            }
        }catch (Exception e) {
            model.addAttribute("updateSuccess", false);
            model.addAttribute("errorMessage", "在庫数の更新中にエラーが発生しました" + e.getMessage());
        }

        List<Order> orders = orderService.findAll();
        model.addAttribute("orderList", orders);

        List<Product> products = productService.findAll();
        model.addAttribute("productList", products);
        return "admin/adminManagement";
    }

}
