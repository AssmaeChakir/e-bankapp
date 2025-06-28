package com.bankapp.bank_management.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ClientRequest {

    @NotBlank(message = "First name is required")
    public String firstName;

    @NotBlank(message = "Last name is required")
    public String lastName;

    @NotBlank(message = "Identity number is required")
    public String identityNumber;

    @NotNull(message = "Birth date is required")
    public LocalDate birthDate;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    public String email;

    @NotBlank(message = "Postal address is required")
    public String postalAddress;
}
