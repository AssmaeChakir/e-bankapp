package com.bankapp.bank_management.repository;

import com.bankapp.bank_management.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByIdentityNumber(String identityNumber);
    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);
    Optional<Client> findByLogin(String login);  // Add this
    Optional<Client> findByIdentityNumber(String identityNumber);  // Add this line


}
