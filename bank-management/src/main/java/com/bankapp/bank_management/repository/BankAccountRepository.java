package com.bankapp.bank_management.repository;

import com.bankapp.bank_management.models.BankAccount;
import com.bankapp.bank_management.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    Optional<BankAccount> findByClient_Email(String email);

    Optional<BankAccount> findByClient(Client client);

    List<BankAccount> findAllByClient(Client client);

}
