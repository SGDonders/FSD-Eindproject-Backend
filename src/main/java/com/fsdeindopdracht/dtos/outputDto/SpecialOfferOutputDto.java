package com.fsdeindopdracht.dtos.outputDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class SpecialOfferOutputDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double discount;
    private Boolean enabled;
}