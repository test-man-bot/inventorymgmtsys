package com.ims.inventorymgmtsys.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String id; //PK
    private String userName;
    private String emailAddress;
    private String address;

    private String phone;

    private String password;

    private boolean enabled;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String customerName) {
        this.userName = customerName;
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

    public Boolean getEnabled() {return enabled;}

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
