package com.fsdeindopdracht.dtos.outputDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ProductOutputDto {
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
