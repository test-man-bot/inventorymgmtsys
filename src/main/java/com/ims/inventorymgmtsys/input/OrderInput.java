package com.ims.inventorymgmtsys.input;

import com.ims.inventorymgmtsys.enumeration.PaymentMethod;

import java.io.Serializable;

public class OrderInput implements Serializable {
    private String name;
    private String address;
    private String phone;
    private String emailAddress;
    private PaymentMethod paymentMethod;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
