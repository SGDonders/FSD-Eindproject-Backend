package com.fsdeindopdracht.dtos.inputDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductInputDto {
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
