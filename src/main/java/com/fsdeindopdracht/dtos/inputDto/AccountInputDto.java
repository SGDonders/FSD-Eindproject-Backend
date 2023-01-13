package com.fsdeindopdracht.dtos.inputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountInputDto {

    public Long id;
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String adress;
    public String zipCode;
    public Long phoneNumber;
    public String email;
    public boolean enabled;
}
