package com.bankapp.bank_management.controller;

import com.bankapp.bank_management.dto.ClientRequest;
import com.bankapp.bank_management.models.Client;
import com.bankapp.bank_management.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @PreAuthorize("hasRole('AGENT_GUICHET')")
    @PostMapping("/add")
    public ResponseEntity<?> addClient(@Valid @RequestBody ClientRequest req) {
        try {
            Client client = clientService.addClient(req);
            return ResponseEntity.ok(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la création du client.");
        }
    }
}
