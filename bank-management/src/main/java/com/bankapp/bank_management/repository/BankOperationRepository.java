package com.bankapp.bank_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bankapp.bank_management.models.BankAccount;
import com.bankapp.bank_management.models.BankOperation;

import java.util.Optional;

public interface BankOperationRepository extends JpaRepository<BankOperation, Long> {
    Page<BankOperation> findByBankAccountOrderByOperationDateDesc(BankAccount bankAccount, Pageable pageable);
    Optional<BankOperation> findTopByBankAccountOrderByOperationDateDesc(BankAccount account);

}
