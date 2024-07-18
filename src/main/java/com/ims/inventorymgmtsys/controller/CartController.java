package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.CartItemInput;
import com.ims.inventorymgmtsys.input.OrderInput;
import com.ims.inventorymgmtsys.service.CatalogService;
import com.ims.inventorymgmtsys.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CatalogService catalogService;
    private final OrderService orderService;
    private final SessionController sessionController;

    public CartController(CatalogService catalogService, OrderService orderService, SessionController sessionController) {
        this.catalogService = catalogService;
        this.orderService = orderService;
        this.sessionController = sessionController;
    }

    @PostMapping("/add-item")
    public String addToCart(@Validated CartItemInput cartItemInput, BindingResult bindingResult, Model model) {
        Product product = catalogService.findById(cartItemInput.getProductId());
        if(bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "catalog/productDetails";
        }
        cartItemInput.setId(UUID.randomUUID().toString());
        cartItemInput.setProductName(product.getName());
        cartItemInput.setProductPrice(product.getPrice());
        CartInput cartInput = sessionController.getCartInput();
        if (cartInput == null) {
            cartInput = new CartInput();
            cartInput.setCartItemInputs(new ArrayList<>());
            sessionController.setCartInput(cartInput);
        }
        cartInput.getCartItemInputs().add(cartItemInput);
        calculatedAmounts(cartInput);
        model.addAttribute("cartInput", cartInput);
        return "cart/cartItem";
    }

    @GetMapping("/display-cart")
    public String displayCart(Model model) {
        model.addAttribute("cartInput", sessionController.getCartInput());
        return "cart/cartItems";
    }

    @PostMapping("remove-item")
    public String removeItem(String cartItemId, Model model) {
        CartInput cartInput = sessionController.getCartInput();
        CartItemInput target = null;
        for (CartItemInput item : cartInput.getCartItemInputs()) {
            if (cartItemId.equals(item.getId())) {
                target = item;
                break;
            }
        }
        cartInput.getCartItemInputs().remove(target);
        calculatedAmounts(cartInput);
        model.addAttribute("cartInput", cartInput);
        return "cart/cartItem";
    }


    private void calculatedAmounts(CartInput cartInput) {
        int totalAmount = orderService.calculateTotalAmount(cartInput.getCartItemInputs());
        int billingAmount = orderService.calculateTax(totalAmount);
        cartInput.setTotalAmount(totalAmount);
        cartInput.setBillingAmount(billingAmount);
    }

}

