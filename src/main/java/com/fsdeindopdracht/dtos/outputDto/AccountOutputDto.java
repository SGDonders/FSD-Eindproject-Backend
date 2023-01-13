package com.fsdeindopdracht.dtos.outputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountOutputDto {

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
