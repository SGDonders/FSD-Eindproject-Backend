package com.fsdeindopdracht.models;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Long id;

    public String name;
    public Double height;
    public Double weight;
    public String size;
    public String description;
    public String packagingMaterial;
    public Long quantity;
    public String category;
}
