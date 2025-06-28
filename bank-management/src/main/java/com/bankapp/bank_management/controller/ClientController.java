package com.bankapp.bank_management.controller;

import com.bankapp.bank_management.dto.ClientProfileDTO;
import com.bankapp.bank_management.dto.ClientRequest;
import com.bankapp.bank_management.models.Client;
import com.bankapp.bank_management.services.BankAccountService;
import com.bankapp.bank_management.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final BankAccountService bankAccountService;

    @Autowired
    public ClientController(ClientService clientService, BankAccountService bankAccountService) {
        this.clientService = clientService;
        this.bankAccountService = bankAccountService;
    }

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

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/profile")
    public ClientProfileDTO getClientProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Client client = bankAccountService.getClientByUsername(userDetails.getUsername());

        return new ClientProfileDTO(
                client.getFirstName(),
                client.getLastName(),
                client.getIdentityNumber(),
                client.getBirthDate(),
                client.getEmail(),
                client.getPostalAddress()
        );
    }

}

