package com.bankapp.bank_management.dto;

import com.bankapp.bank_management.models.BankOperation;
import com.bankapp.bank_management.models.OperationType;

import java.time.LocalDateTime;

public class BankOperationDTO {

    private String label;
    private OperationType type;
    private LocalDateTime operationDate;
    private Double amount;

    public BankOperationDTO(String label, OperationType type, LocalDateTime operationDate, Double amount) {
        this.label = label;
        this.type = type;
        this.operationDate = operationDate;
        this.amount = amount;
    }

    public static BankOperationDTO fromEntity(BankOperation operation) {
        return new BankOperationDTO(
                operation.getLabel(),
                operation.getType(),
                operation.getOperationDate(),
                operation.getAmount()
        );
    }

    // Getters for Jackson
    public String getLabel() {
        return label;
    }

    public OperationType getType() {
        return type;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public Double getAmount() {
        return amount;
    }

    // You can add setters if needed, or omit if immutable
}
