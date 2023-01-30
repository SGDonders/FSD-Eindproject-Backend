
package com.fsdeindopdracht.dtos.registerDto;

import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.models.Authority;
import com.fsdeindopdracht.models.User;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterDto {

    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String address;
    public String zipCode;
    public String phoneNumber;
    public String email;

    public Account account;

    public User user;

//    public String role;

    public Set<Authority> authorities;
}