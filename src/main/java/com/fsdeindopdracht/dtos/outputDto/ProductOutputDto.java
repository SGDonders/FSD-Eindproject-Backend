package com.fsdeindopdracht.dtos.outputDto;

import com.fsdeindopdracht.models.FileDocument;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ProductOutputDto {
    public String productName;

    public Long id;
    public Double price;
    public Double availableStock;
    public String category;

    public FileDocument fileDocument;
}
