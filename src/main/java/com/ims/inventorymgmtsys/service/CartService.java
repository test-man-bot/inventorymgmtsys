package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.CartItemInput;

public interface CartService {
    CartInput getCartInput();
    Product getProduct(String productId);
    void setCartInput(CartInput cartInput);
    void addItemToCart(CartItemInput cartItemInput);
    void removeItemFromCart(String cartItemId);
    void updateItemQuantity(String cartItemId, int quantity);
    void calculatedAmounts(CartInput cartInput);
}
