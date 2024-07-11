package com.ims.inventorymgmtsys.entity;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private String orderId; //FK
    private String productId; //FK

    private Integer quantity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) { this.orderId = orderId; }


    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) { this.productId = productId;}

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
