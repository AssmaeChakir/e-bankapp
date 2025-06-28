package com.bankapp.bank_management.controller;

import com.bankapp.bank_management.dto.CreateAccountRequest;
import com.bankapp.bank_management.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService accountService;
    @PreAuthorize("hasRole('AGENT_GUICHET')")
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        String result = accountService.createBankAccount(request.getRib(), request.getIdentityNumber());
        if (result.equals("Compte bancaire créé avec succès.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
