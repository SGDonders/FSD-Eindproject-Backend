package com.fsdeindopdracht.dtos.registerDto;

import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.models.Authority;
import com.fsdeindopdracht.models.User;

import javax.validation.constraints.*;

import java.util.Set;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters long")
    public String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    public String password;

    @NotBlank(message = "First name is required")
    @Size(max = 15, message = "First name cannot exceed 15 characters")
    public String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    public String lastName;

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Address cannot exceed 100 characters")
    public String address;

    @NotBlank(message = "Zip code is required")
    public String zipCode;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number")
    public String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    public String email;

    private Account account;

    private User user;

    private Set<Authority> authorities;
}