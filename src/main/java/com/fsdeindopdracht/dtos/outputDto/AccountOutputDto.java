package com.fsdeindopdracht.dtos.outputDto;

import com.fsdeindopdracht.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AccountOutputDto {

    public String userName;
    public String firstName;
    public String lastName;
    public String address;
    public String zipCode;
    public String phoneNumber;
    public String email;

    private User user;
}