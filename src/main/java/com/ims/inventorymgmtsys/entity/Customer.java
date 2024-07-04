package com.ims.inventorymgmtsys.entity;

import java.io.Serializable;

public class Customer implements Serializable {
    private String id; //PK
    private String customerName;
    private String emailAdress;
    private String address;

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


    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
