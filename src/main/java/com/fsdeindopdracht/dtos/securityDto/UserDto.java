package com.fsdeindopdracht.dtos.securityDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String adress;
    private String city;
    private String region;
    private Long phone;
    private String email;

}
