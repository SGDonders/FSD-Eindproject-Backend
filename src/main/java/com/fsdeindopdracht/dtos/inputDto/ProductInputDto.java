package com.fsdeindopdracht.dtos.inputDto;

import com.fsdeindopdracht.models.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductInputDto {
    public String productName;
    public Double price;
    public Double availableStock;
    public String category;

    public Image image;
}
