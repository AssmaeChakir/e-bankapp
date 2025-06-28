package com.bankapp.bank_management.services;

import com.bankapp.bank_management.dto.ClientRequest;
import com.bankapp.bank_management.models.Client;
import com.bankapp.bank_management.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject password encoder

    public Client addClient(ClientRequest req) {
        if (req.firstName == null || req.lastName == null || req.birthDate == null
                || req.email == null || req.postalAddress == null || req.identityNumber == null) {
            throw new IllegalArgumentException("Tous les champs sont obligatoires");
        }

        if (clientRepository.existsByIdentityNumber(req.identityNumber)) {
            throw new IllegalArgumentException("Numéro d'identité déjà utilisé");
        }

        if (clientRepository.existsByEmail(req.email)) {
            throw new IllegalArgumentException("Adresse mail déjà utilisée");
        }

        // Generate login and raw password
        String login = req.email;
        String rawPassword = generateRandomPassword();

        Client client = new Client();
        client.setFirstName(req.firstName);
        client.setLastName(req.lastName);
        client.setIdentityNumber(req.identityNumber);
        client.setBirthDate(req.birthDate);
        client.setEmail(req.email);
        client.setPostalAddress(req.postalAddress);
        client.setLogin(login);

        // Hash password before saving
        client.setPassword(passwordEncoder.encode(rawPassword));

        Client saved = clientRepository.save(client);

        // Send email with raw password (not hashed)
        emailService.sendCredentialsEmail(client.getEmail(), login, rawPassword);

        return saved;
    }

    private String generateRandomPassword() {
        return "pass" + new Random().nextInt(9999); // Consider more secure generator for production
    }
}
