package com.fsdeindopdracht.dtos.inputDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter

public class SpecialOfferInputDto {
    @Null(message = "ID must not be provided")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Discount is required")
    @Min(value = 0, message = "Discount must be a non-negative number")
    @Max(value = 100, message = "Discount must be a percentage between 0 and 100")
    private Double discount;

    @NotNull(message = "Enabled flag is required")
    private Boolean enabled;
}
