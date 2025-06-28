package com.bankapp.bank_management.dto;

import com.bankapp.bank_management.models.AccountStatus;

public class BankAccountSummaryDTO {

    private String rib;
    private String clientFullName; // add this field
    private double balance;
    private AccountStatus status;

    // Constructor with all fields
    public BankAccountSummaryDTO(String rib, String clientFullName, double balance, AccountStatus status) {
        this.rib = rib;
        this.clientFullName = clientFullName;
        this.balance = balance;
        this.status = status;
    }

    // getters and setters

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
