package com.ims.inventorymgmtsys.entity;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private String orderId; //FK
    private String productId; //FK

    private Integer quantity;

    public String getOrderId() {
        return orderId;
    }


    public String getProductId() {
        return productId;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
