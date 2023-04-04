package com.fsdeindopdracht.dtos.inputDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OrderInputDto {
    @Id
    private Long id;
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String userName;

    @NotNull(message = "Order total is required")
    @DecimalMin(value = "0.0", message = "Order total must be a positive number")
    private Double orderTotal;

    private LocalDate orderDate;

    @NotBlank(message = "Pickup date is required")
    private String pickUpDate;

    @NotNull(message = "Time frame is required")
    private Boolean timeFrame;

    @NotEmpty(message = "Product names are required")
    private List<@NotBlank(message = "Product name is required") String> productNames;


}
