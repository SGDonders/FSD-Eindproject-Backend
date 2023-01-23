package com.fsdeindopdracht.models;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String Address;
    private String phoneNumber;
    private String email;


    @OneToOne(
            targetEntity = User.class,
            mappedBy = "account")
    private User user;



}
