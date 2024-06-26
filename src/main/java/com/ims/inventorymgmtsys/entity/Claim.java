package com.ims.inventorymgmtsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Claim implements Serializable {
    private String claimId; //PK
    private LocalDateTime claimDateTime;
    private String deliverId; //FK
    private String employeeId; //FK

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public LocalDateTime getClaimDateTime() {
        return claimDateTime;
    }

    public void setClaimDateTime(LocalDateTime claimDateTime) {
        this.claimDateTime = claimDateTime;
    }

    public String getDeliverId() {
        return deliverId;
    }


    public String getEmployeeId() {
        return employeeId;
    }

}
