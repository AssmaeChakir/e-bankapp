package com.bankapp.bank_management.controller;

import com.bankapp.bank_management.dto.BankAccountSummaryDTO;
import com.bankapp.bank_management.dto.BankOperationDTO;
import com.bankapp.bank_management.dto.TransferRequestDTO;
import com.bankapp.bank_management.models.BankAccount;
import com.bankapp.bank_management.models.BankOperation;
import com.bankapp.bank_management.models.OperationType;
import com.bankapp.bank_management.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class BankOperationController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankOperationController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/me")
    public BankAccountSummaryDTO getMyAccountDetails(@AuthenticationPrincipal UserDetails userDetails) {
        BankAccount account = bankAccountService.getAccountForClient(userDetails.getUsername());

        return new BankAccountSummaryDTO(
                account.getRib(),
                account.getClient().getFirstName() + " " + account.getClient().getLastName(),
                account.getBalance(),
                account.getStatus()
        );
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/my-accounts")
    public List<BankAccountSummaryDTO> getMyAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        List<BankAccount> accounts = bankAccountService.getAccountsForClient(userDetails.getUsername());

        return accounts.stream()
                .map(account -> new BankAccountSummaryDTO(
                        account.getRib(),
                        account.getClient().getFirstName() + " " + account.getClient().getLastName(),
                        account.getBalance(),
                        account.getStatus()))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/operations")
    public Page<BankOperationDTO> getOperations(
            @RequestParam String rib,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Verify that the user owns the account
        List<BankAccount> userAccounts = bankAccountService.getAccountsForClient(userDetails.getUsername());
        boolean ownsAccount = userAccounts.stream()
                .anyMatch(account -> account.getRib().equals(rib));
        if (!ownsAccount) {
            throw new IllegalArgumentException("Unauthorized access to this account's operations");
        }

        return bankAccountService.getOperationsByAccount(rib, page, size)
                .map(BankOperationDTO::fromEntity);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping("/transfer")
    public ResponseEntity<String> createTransfer(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TransferRequestDTO transferRequest) {

        List<BankAccount> userAccounts = bankAccountService.getAccountsForClient(userDetails.getUsername());

        BankAccount source = userAccounts.stream()
                .filter(account -> account.getRib().equals(transferRequest.getFromRib()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unauthorized access to the source account"));

        // RG_11: Check account not blocked/closed
        if (source.getStatus().equals("BLOQUE") || source.getStatus().equals("CLOTURE")) {
            throw new IllegalArgumentException("Le compte est bloqué ou clôturé");
        }

        // RG_12: Sufficient balance
        if (source.getBalance() < transferRequest.getAmount()) {
            throw new IllegalArgumentException("Solde insuffisant pour effectuer le virement");
        }

        // RG_13: Debit source
        bankAccountService.addOperation(
                transferRequest.getFromRib(),
                "Virement débité vers " + transferRequest.getToRib() + " | Motif: " + transferRequest.getMotif(),
                OperationType.DEBIT,
                transferRequest.getAmount()
        );

        // RG_14: Credit destination
        bankAccountService.addOperation(
                transferRequest.getToRib(),
                "Virement reçu de " + transferRequest.getFromRib() + " | Motif: " + transferRequest.getMotif(),
                OperationType.CREDIT,
                transferRequest.getAmount()
        );

        // RG_15 is satisfied: both operations recorded with timestamp by addOperation

        return ResponseEntity.ok("Virement effectué avec succès.");
    }

}
