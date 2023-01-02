package com.fsdeindopdracht.dtos.outputDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class SpecialOfferOutputDto {
    public Long id;
    public String name;
    public String description;
    public LocalDate startDate;
    public LocalDate endDate;
    public Double discount;
    public Boolean enabled;
}