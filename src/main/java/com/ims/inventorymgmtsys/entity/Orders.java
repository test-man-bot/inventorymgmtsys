package com.ims.inventorymgmtsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Orders implements Serializable {

    private String orderId; //PK
    private LocalDateTime orderDateTime;

    private String customerName; //FK
    private String employeeName; //FK

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getCustomerName() {
        return customerName;
    }


    public String getEmployeeName() {
        return employeeName;
    }


}
