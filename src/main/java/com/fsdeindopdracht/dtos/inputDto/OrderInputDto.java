package com.fsdeindopdracht.dtos.inputDto;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter

public class OrderInputDto {


    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String userName;

    @NotNull(message = "Order total is required")
    @DecimalMin(value = "0.0", message = "Order total must be a positive number")
    private Double orderTotal;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @NotBlank(message = "Pickup date is required")
    private String pickUpDate;

    @NotNull(message = "Time frame is required")
    private Boolean timeFrame;

    @NotEmpty(message = "Product names are required")
    private List<@NotBlank(message = "Product name is required") String> productNames;

}
