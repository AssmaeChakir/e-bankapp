package com.bankapp.bank_management.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_operations")
public class BankOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;  // description of the operation

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType type;  // DEBIT or CREDIT

    @Column(nullable = false)
    private LocalDateTime operationDate = LocalDateTime.now();

    @Column(nullable = false)
    private Double amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private BankAccount bankAccount;

    // Constructors
    public BankOperation() {}

    public BankOperation(String label, OperationType type, Double amount, BankAccount bankAccount) {
        this.label = label;
        this.type = type;
        this.amount = amount;
        this.bankAccount = bankAccount;
        this.operationDate = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
