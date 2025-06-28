package com.bankapp.bank_management.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data               // Lombok annotation to generate getters, setters, toString, equals, and hashCode
@NoArgsConstructor  // Lombok: creates a no-args constructor
@AllArgsConstructor // Lombok: creates an all-args constructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto increment id
    private Long id;

    @Column(nullable = false, unique = true)  // username must be unique & not null
    private String username;

    @Column(nullable = false)  // password must not be null
    private String password;  // stored encrypted (hashed)

    @Enumerated(EnumType.STRING)  // store role as string, not ordinal
    @Column(nullable = false)
    private Role role;  // CLIENT or AGENT_GUICHET
}
