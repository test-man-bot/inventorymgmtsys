package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.service.CatalogService;
import com.ims.inventorymgmtsys.service.OrderService;
import com.ims.inventorymgmtsys.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
//        List<Order> orders = orderService.findAll();
//        List<Product> products = catalogService.findAll();
//        model.addAttribute("orderList", orders);
//        model.addAttribute("productList", products);
        ProductWrapper productWrapper = new ProductWrapper();
        List<Product> products = productService.findAll(); // 商品リストを取得
        productWrapper.setProducts(products); // 商品リストを設定

        model.addAttribute("productWrapper", productWrapper);
        // 他の必要なモデル属性も設定
        model.addAttribute("orderList", orderService.findAll());
        return "admin/adminManagement";
    }

//    @PostMapping("update-product")
//    public String updateProduct(@RequestParam("id") String id, @RequestParam("type") String type, @RequestParam("value") String value, Model model) {
//        try {
//            Product product = productService.findById(id);
//
//            if (product != null) {
//                switch (type) {
//                    case "name":
//                        product.setName(value);
//                        model.addAttribute("updateSuccess", "商品名を更新しました");
//                        break;
//                    case "price":
//                        product.setPrice(Integer.valueOf(value));
//                        model.addAttribute("updateSuccess", "価格を更新しました");
//                        break;
//                    case "stock":
//                        product.setStock(Integer.valueOf(value));
//                        model.addAttribute("updateSuccess", "在庫数を更新しました");
//                        break;
//                    default:
//                        model.addAttribute("errorMessage", "無効な更新タイプです");
//                        return populateModel(model);
//                }
//
//                boolean isUpdate = productService.save(product);
//                if (!isUpdate) {
//                    model.addAttribute("updateSuccess", false);
//                }
//            } else {
//                model.addAttribute("errorMessage", "商品が見つかりません");
//            }
//        } catch (Exception e) {
//            model.addAttribute("updateSuccess", false);
//            model.addAttribute("errorMessage", "商品の更新中にエラーが発生しました。" + e.getMessage());
//        }
//
//        return populateModel(model);
//    }
//        private String populateModel(Model model) {
//            List<Order> orders = orderService.findAll();
//            model.addAttribute("orderList", orders);
//
//            List<Product> products = productService.findAll();
//            model.addAttribute("productList", products);
//            return "admin/adminManagement";
//        }
//    }
//    @PostMapping("update-productprice")
//    public String updatePrice(@RequestParam("id") String id, @RequestParam("price") int price, Model model) {
//        try {
//            Product product = productService.findById(id);
//
//            if (product != null) {
//                product.setPrice(price);
//                boolean isUpdate = productService.save(product);
//                if (isUpdate) {
//                    model.addAttribute("updateSuccess", "価格を更新しました。");
//                }
//            } else {
//                model.addAttribute("errorMessage", false);
//            }
//        } catch (Exception e) {
//            model.addAttribute("updateSuccess", false);
//            model.addAttribute("errorMessage", "価格の更新中にエラーが発生しました。" + e.getMessage());
//        }
//        List<Order> orders = orderService.findAll();
//        model.addAttribute("orderList", orders);
//
//        List<Product> products = productService.findAll();
//        model.addAttribute("productList", products);
//        return "admin/adminManagement";
//
//    }
//
//    @PostMapping("update-productname")
//    public String updateName(@RequestParam("id") String id, @RequestParam("name") String name, Model model) {
//        try {
//            Product product = productService.findById(id);
//
//            if (product != null) {
//                product.setName(name);
//                boolean isUpdate = productService.save(product);
//                if (isUpdate) {
//                    model.addAttribute("updateSuccess", "商品名を更新しました。");
//                }
//            } else {
//                model.addAttribute("errorMessage", false);
//            }
//        } catch (Exception e) {
//            model.addAttribute("updateSuccess", false);
//            model.addAttribute("errorMessage", "商品名の更新中にエラーが発生しました。" + e.getMessage());
//        }
//        List<Order> orders = orderService.findAll();
//        model.addAttribute("orderList", orders);
//
//        List<Product> products = productService.findAll();
//        model.addAttribute("productList", products);
//        return "admin/adminManagement";
//
//    }
//
//    @PostMapping("update-productstock")
//    public String updateStock(@RequestParam("id") String id, @RequestParam("stock") int stock, Model model) {
//        try {
//            Product product = productService.findById(id);
//
//            if (product != null) {
//                product.setStock(stock);
//                boolean isUpdate = productService.save(product);
//
//                model.addAttribute("updateSuccess", isUpdate);
//            } else {
//                model.addAttribute("updateSuccess", false);
//            }
//        }catch (Exception e) {
//            model.addAttribute("updateSuccess", false);
//            model.addAttribute("errorMessage", "在庫数の更新中にエラーが発生しました" + e.getMessage());
//        }
//
//        List<Order> orders = orderService.findAll();
//        model.addAttribute("orderList", orders);
//
//        List<Product> products = productService.findAll();
//        model.addAttribute("productList", products);
//        return "admin/adminManagement";
//    }

    @PostMapping("/update-products")
    public String updateProducts(@ModelAttribute("productWrapper") ProductWrapper productWrapper, Model model) {
        List<Product> products = productWrapper.getProducts();
        try {
            for (Product product : products) {
                Product existingProduct = productService.findById(product.getId());
                if (existingProduct != null) {
                    existingProduct.setName(product.getName());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setStock(product.getStock());
                    productService.save(existingProduct);
                }
            }
            model.addAttribute("updateSuccess", "すべての商品を更新しました。");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "商品の更新中にエラーが発生しました。" + e.getMessage());
        }

            List<Order> orders = orderService.findAll();
            model.addAttribute("orderList", orders);
            model.addAttribute("productList", productService.findAll());
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

//        private String populateModel(Model model) {
//            List<Order> orders = orderService.findAll();
//            model.addAttribute("orderList", orders);
//
//            List<Product> products = productService.findAll();
//            model.addAttribute("productList", products);
//            return "admin/adminManagement";
//        }

}
