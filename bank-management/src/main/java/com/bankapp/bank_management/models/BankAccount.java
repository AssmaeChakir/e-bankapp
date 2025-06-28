package com.bankapp.bank_management.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @Column(unique = true, nullable = false)
    private String rib;  // Use this as the primary key (NO @GeneratedValue)

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.OUVERT;

    @Column(nullable = false)
    private Double balance = 0.0;

    private LocalDate createdAt = LocalDate.now();

    // Getters and Setters

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
