package com.fsdeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table(name = "account")
public class Account {

    @Id
    private String userName;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String Address;
    private String phoneNumber;
    private String email;

    @OneToOne(
            targetEntity = User.class,
            mappedBy = "account")
    @JsonIgnore
    private User user;


    public Account(String userName, String firstName, String lastName, String zipCode, String address, String phoneNumber, String email) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        Address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
