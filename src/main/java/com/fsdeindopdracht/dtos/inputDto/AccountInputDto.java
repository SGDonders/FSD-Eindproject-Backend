package com.fsdeindopdracht.dtos.inputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountInputDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
}
