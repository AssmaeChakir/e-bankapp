package com.bankapp.bank_management.services;

import com.bankapp.bank_management.models.Client;
import com.bankapp.bank_management.models.User;
import com.bankapp.bank_management.repository.ClientRepository;
import com.bankapp.bank_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CombinedUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Trying to find user with username/login: " + username);

        return clientRepository.findByLogin(username)
                .map(client -> {
                    System.out.println("Found client with login: " + client.getLogin());
                    return buildUserDetailsFromClient(client);
                })
                .orElseGet(() -> {
                    System.out.println("Client not found, trying user repository");
                    return userRepository.findByUsername(username)
                            .map(user -> {
                                System.out.println("Found user with username: " + user.getUsername());
                                return buildUserDetailsFromUser(user);
                            })
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                });
    }

    private UserDetails buildUserDetailsFromClient(Client client) {
        return new org.springframework.security.core.userdetails.User(
                client.getLogin(),
                client.getPassword(),
                Collections.singleton(() -> "ROLE_CLIENT")
        );
    }

    private UserDetails buildUserDetailsFromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(() -> "ROLE_" + user.getRole().name())
        );
    }
}
