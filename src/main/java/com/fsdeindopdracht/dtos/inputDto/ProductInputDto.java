package com.fsdeindopdracht.dtos.inputDto;

import com.fsdeindopdracht.models.Image;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter

public class ProductInputDto {

    @NotBlank(message = "Product name is required")
    @Size(max = 50, message = "Product name cannot exceed 50 characters")
    private String productName;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be a positive number")
    private Double price;

    @NotNull(message = "Available stock is required")
    @DecimalMin(value = "0.0", message = "Available stock must be a positive number")
    private Double availableStock;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category cannot exceed 50 characters")
    private String category;
    public Image image;
}
