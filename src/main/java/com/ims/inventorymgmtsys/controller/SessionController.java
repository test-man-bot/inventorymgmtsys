package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.OrderInput;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.lang.reflect.Proxy;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@SuppressWarnings("serial")
public class SessionController implements Serializable {
    private CartInput cartInput;
    private OrderInput orderInput;

    public CartInput getCartInput() {
        return cartInput;
    }
    public void setCartInput(CartInput cartInput) {
        this.cartInput = cartInput;
    }

    public OrderInput getOrderInput() {
        return  orderInput;
    }

    public void setOrderInput(OrderInput orderInput) {
        this.orderInput = orderInput;
    }

    public void clearData() {
        cartInput = null;
        orderInput = null;
    }
}
