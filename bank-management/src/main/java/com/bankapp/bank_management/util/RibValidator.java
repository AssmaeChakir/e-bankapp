package com.bankapp.bank_management.util;

public class RibValidator {
    public static boolean isValid(String rib) {
        return rib != null && rib.matches("\\d{24}"); // Simplified: 24 digits
    }
}