package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.service.CatalogService;
import com.ims.inventorymgmtsys.service.EmployeeService;
import com.ims.inventorymgmtsys.service.OrderService;
import com.ims.inventorymgmtsys.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;
    private final CatalogService catalogService;

    private final ProductService productService;
    private final EmployeeService employeeService;

    public AdminController(CatalogService catalogService, OrderService orderService, ProductService productService, EmployeeService employeeService) {
        this.orderService = orderService;
        this.catalogService = catalogService;
        this.productService = productService;
        this.employeeService = employeeService;
    }

    @GetMapping("/management")
    public String displayList(Model model) {
        ProductWrapper productWrapper = new ProductWrapper();
        List<Product> products = productService.findAll(); // 商品リストを取得
        productWrapper.setProducts(products); // 商品リストを設定

        model.addAttribute("productWrapper", productWrapper);
        // 他の必要なモデル属性も設定
        model.addAttribute("orderList", orderService.findAll());
        model.addAttribute("newProduct", new Product());
        return "admin/adminManagement";
    }


    @PostMapping("/update-products")
    public String updateProducts(@ModelAttribute("productWrapper") ProductWrapper productWrapper, Model model) {
        List<Product> products = productWrapper.getProducts();
        boolean isUpdate = false;
        try {
            for (Product product : products) {
                Product existingProduct = productService.findById(product.getId());
                if (existingProduct != null) {

                    if(!existingProduct.getName().equals(product.getName())){
                        existingProduct.setName(product.getName());
                        isUpdate = true;
                    }
                    if (!existingProduct.getPrice().equals(product.getPrice())){
                        existingProduct.setPrice(product.getPrice());
                        isUpdate = true;
                    }
                    if (!existingProduct.getStock().equals(product.getStock())){
                        existingProduct.setStock(product.getStock());
                        isUpdate = true;
                    }
                    if(isUpdate) {
                        productService.update(existingProduct);
                        model.addAttribute("successMessage", "商品を更新しました。");

                    }

                }
            }
            if (!isUpdate) {
                model.addAttribute("successMessage", "更新はありません");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "商品の更新中にエラーが発生しました。" + e.getMessage());
        }

            List<Order> orders = orderService.findAll();
            model.addAttribute("orderList", orders);
            model.addAttribute("productWrapper", new ProductWrapper(productService.findAll()));
            model.addAttribute("newProduct", new Product());
            return "admin/adminManagement";
        }
    public static class ProductWrapper {
        private List<Product> products;

        public ProductWrapper() {
            // Default constructor
        }

        public ProductWrapper(List<Product> products) {
            this.products = products;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }

    @PostMapping("add-product")
    public String addProduct(@ModelAttribute("newProduct") @Valid Product product, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errorMessage2", "商品の追加中にエラーが発生しました");
            return "admin/adminManagement";
        }

        try {
            productService.save(product);
            model.addAttribute("successMessage2", "商品を追加しました");
        }catch (Exception e){
            model.addAttribute("errorMessage2", "商品の追加中にエラーが発生しました" + e.getMessage());
        }
        model.addAttribute("orderList", orderService.findAll());
        model.addAttribute("productWrapper", new ProductWrapper(productService.findAll()));
        return "admin/adminManagement";
    }

    @GetMapping("/orders")
    public String displayOrderList(Model model) {
        model.addAttribute("orderList", orderService.findAll());
        return "admin/adminOrders";
    }

    @GetMapping("/employees")
    public String displayEmployeeList(Model model) {
        model.addAttribute("employeeList", employeeService.findAll());
        return "admin/adminEmployees";
    }


}
