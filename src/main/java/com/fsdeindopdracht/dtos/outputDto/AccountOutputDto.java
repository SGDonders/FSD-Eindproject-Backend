package com.fsdeindopdracht.dtos.outputDto;

import com.fsdeindopdracht.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountOutputDto {

    public String userName;
    public String firstName;
    public String lastName;
    public String adress;
    public String zipCode;
    public String phoneNumber;
    public String email;

    private User user;
}