package com.bankapp.bank_management.dto;

import java.time.LocalDate;

public class ClientProfileDTO {

    private String firstName;
    private String lastName;
    private String identityNumber;
    private LocalDate birthDate;
    private String email;
    private String postalAddress;

    public ClientProfileDTO(String firstName, String lastName, String identityNumber,
                            LocalDate birthDate, String email, String postalAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityNumber = identityNumber;
        this.birthDate = birthDate;
        this.email = email;
        this.postalAddress = postalAddress;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getIdentityNumber() { return identityNumber; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getEmail() { return email; }
    public String getPostalAddress() { return postalAddress; }
}
