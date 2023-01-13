package com.fsdeindopdracht.dtos.outputDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ProductOutputDto {
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
