package com.fsdeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String productName;

    private Double price;
    private Double availableStock;
    private String category;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Order> orders;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private FileDocument fileDocument;
}
