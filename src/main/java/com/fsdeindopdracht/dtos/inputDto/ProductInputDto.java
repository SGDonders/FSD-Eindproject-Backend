package com.fsdeindopdracht.dtos.inputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductInputDto {
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
