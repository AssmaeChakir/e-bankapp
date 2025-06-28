package com.bankapp.bank_management.services;

import com.bankapp.bank_management.models.BankAccount;
import com.bankapp.bank_management.models.BankOperation;
import com.bankapp.bank_management.models.Client;
import com.bankapp.bank_management.models.OperationType;
import com.bankapp.bank_management.repository.BankAccountRepository;
import com.bankapp.bank_management.repository.BankOperationRepository;
import com.bankapp.bank_management.repository.ClientRepository;
import com.bankapp.bank_management.util.RibValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankOperationRepository bankOperationRepository;

    @Autowired
    private ClientRepository clientRepository;

    public String createBankAccount(String rib, String identityNumber) {
        if (!RibValidator.isValid(rib)) {
            return "RIB invalide.";
        }

        Client client = clientRepository.findByIdentityNumber(identityNumber)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec ce numéro d'identité."));

        BankAccount account = new BankAccount();
        account.setRib(rib);
        account.setClient(client);
        // Status is set to OUVERT by default
        bankAccountRepository.save(account);

        return "Compte bancaire créé avec succès.";
    }

    public Page<BankOperation> getOperationsByAccount(String rib, int page, int size) {
        BankAccount account = bankAccountRepository.findById(rib)
                .orElseThrow(() -> new IllegalArgumentException("Compte bancaire introuvable"));

        return bankOperationRepository.findByBankAccountOrderByOperationDateDesc(account, PageRequest.of(page, size));
    }

    @Transactional
    public BankOperation addOperation(String rib, String label, OperationType type, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }

        BankAccount account = bankAccountRepository.findById(rib)
                .orElseThrow(() -> new IllegalArgumentException("Compte bancaire introuvable"));

        // Update balance depending on operation type
        if (type == OperationType.DEBIT) {
            if (account.getBalance() < amount) {
                throw new IllegalArgumentException("Solde insuffisant");
            }
            account.setBalance(account.getBalance() - amount);
        } else if (type == OperationType.CREDIT) {
            account.setBalance(account.getBalance() + amount);
        }

        bankAccountRepository.save(account);

        BankOperation operation = new BankOperation();
        operation.setBankAccount(account);
        operation.setLabel(label);
        operation.setType(type);
        operation.setAmount(amount);
        operation.setOperationDate(LocalDateTime.now());

        return bankOperationRepository.save(operation);
    }

    // Get one account for a client (useful when there's only one account)
    public BankAccount getAccountForClient(String username) {
        Client client = clientRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable"));

        return bankAccountRepository.findByClient(client)
                .orElseThrow(() -> new IllegalArgumentException("Compte bancaire introuvable pour ce client"));
    }

    // NEW: Get all accounts for client
    public List<BankAccount> getAccountsForClient(String username) {
        Client client = clientRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable"));

        return bankAccountRepository.findAllByClient(client);
    }
    public Client getClientByUsername(String username) {
        return clientRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
    }


    // NEW: Get most recently moved account (with latest operation)
    public BankAccount getMostRecentlyMovedAccount(String username) {
        List<BankAccount> accounts = getAccountsForClient(username);

        return accounts.stream()
                .max(Comparator.comparing(account -> {
                    Optional<BankOperation> lastOp =
                            bankOperationRepository.findTopByBankAccountOrderByOperationDateDesc(account);
                    return lastOp.map(BankOperation::getOperationDate).orElse(LocalDateTime.MIN);
                }))
                .orElseThrow(() -> new IllegalArgumentException("Aucun compte trouvé pour ce client"));
    }
}
