package com.fsdeindopdracht.dtos.inputDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class SpecialOfferInputDto {
    public Long id;
    public String name;
    public String description;
    public LocalDate startDate;
    public LocalDate endDate;
    public Double discount;
    public Boolean enabled;
}
