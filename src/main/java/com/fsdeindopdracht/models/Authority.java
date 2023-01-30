package com.fsdeindopdracht.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Entity
@IdClass(AuthorityKey.class)
@Table(name = "authorities")
public class Authority implements Serializable {

    @Id
    @Column(nullable = false)
    private String username;

    @Id
    @Column(nullable = false)
    private String authority;

}
