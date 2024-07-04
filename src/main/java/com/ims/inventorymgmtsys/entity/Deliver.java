package com.ims.inventorymgmtsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Deliver implements Serializable {
    private String deliverId; //PK
    private LocalDateTime deliverDateTime;
    private String orderId; //FK
    private String employeeId; //FK

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public LocalDateTime getDeliverDateTime() {
        return deliverDateTime;
    }

    public void setDeliverDateTime(LocalDateTime deliverDateTime) {
        this.deliverDateTime = deliverDateTime;
    }

    public String getOrderId() {
        return orderId;
    }


    public String getEmployeeId() {
        return employeeId;
    }

}
