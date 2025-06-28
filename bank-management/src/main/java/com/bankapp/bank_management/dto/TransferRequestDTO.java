package com.bankapp.bank_management.dto;

public class TransferRequestDTO {
    private String fromRib;
    private String toRib;
    private Double amount;
    private String motif;

    public String getFromRib() {
        return fromRib;
    }

    public void setFromRib(String fromRib) {
        this.fromRib = fromRib;
    }

    public String getToRib() {
        return toRib;
    }

    public void setToRib(String toRib) {
        this.toRib = toRib;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
