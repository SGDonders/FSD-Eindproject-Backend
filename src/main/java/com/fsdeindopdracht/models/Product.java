package com.fsdeindopdracht.models;

import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private Double height;
    private Double weight;
    private String size;
    private String description;
    private String packagingMaterial;
    private Long quantity;
    private String category;
}
