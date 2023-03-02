package com.fsdeindopdracht.dtos.inputDto;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderInputDto {

    private String userName;

    private Double orderTotal;

    private LocalDate orderDate;

    private String pickUpDate;

    private Boolean timeFrame;


    private List<String> productNames;


}
