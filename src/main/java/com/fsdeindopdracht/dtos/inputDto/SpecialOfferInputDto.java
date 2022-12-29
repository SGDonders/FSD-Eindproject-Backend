package com.fsdeindopdracht.dtos.inputDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class SpecialOfferInputDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double discount;
    private Boolean enabled;
}
