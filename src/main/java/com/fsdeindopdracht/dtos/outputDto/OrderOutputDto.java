package com.fsdeindopdracht.dtos.outputDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fsdeindopdracht.models.Order;
import com.fsdeindopdracht.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter

public class OrderOutputDto {
    private Long id;

    private String userName;

    private Double orderTotal;

    private LocalDate orderDate;

    private String pickUpDate;

    private Boolean timeFrame;

    private List<Product> productNames;
}