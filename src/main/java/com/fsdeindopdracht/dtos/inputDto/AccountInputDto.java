package com.fsdeindopdracht.dtos.inputDto;


import com.fsdeindopdracht.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountInputDto {

    public String userName;
    public String firstName;
    public String lastName;
    public String address;
    public String zipCode;
    public String phoneNumber;
    public String email;

    private User user;

}