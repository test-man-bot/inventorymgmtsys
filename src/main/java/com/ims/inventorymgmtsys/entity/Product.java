package com.ims.inventorymgmtsys.entity;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.PrimitiveIterator;
import java.util.UUID;

public class Product implements Serializable {
    private UUID id; //PK
    private String name;
    private Integer price;
    private Integer stock;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
