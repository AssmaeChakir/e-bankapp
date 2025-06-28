package com.bankapp.bank_management.dto;

public class CreateAccountRequest {
    private String rib;
    private String identityNumber;

    // Getters and Setters
    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }
}
