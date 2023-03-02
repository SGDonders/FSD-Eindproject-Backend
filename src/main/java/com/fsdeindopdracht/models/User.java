package com.fsdeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Account account;


    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Order order;

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

    public User(String username) {
        this.username = username;
    }

    public void setOrder(Order order) {
        this.order = order;
        order.setUser(this);
    }

    public void removeOrder() {
        if (order != null) {
            order.setUser(null);
            order = null;
        }
    }


}