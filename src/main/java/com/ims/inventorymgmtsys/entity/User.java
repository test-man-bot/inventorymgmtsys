package com.ims.inventorymgmtsys.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String id; //PK
    private String customerName;
    private String emailAddress;
    private String address;

    private String phone;

    private String password;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAdress) {
        this.emailAddress = emailAdress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

}
