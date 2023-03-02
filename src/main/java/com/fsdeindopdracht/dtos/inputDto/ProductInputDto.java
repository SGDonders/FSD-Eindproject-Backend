package com.fsdeindopdracht.dtos.inputDto;

import com.fsdeindopdracht.models.FileDocument;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter

public class ProductInputDto {
    public String productName;
    public Double price;
    public Double availableStock;
    public String category;

    public FileDocument fileDocument;
}
