package com.fsdeindopdracht.dtos.outputDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ProductOutputDto {
    public String productName;
    public Double price;
    public Double availableStock;
    public String category;
}
