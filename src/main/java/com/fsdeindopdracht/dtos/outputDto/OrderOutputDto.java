package com.fsdeindopdracht.dtos.outputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderOutputDto {
    private Long id;
    private String orderHeader;
    private String comment;
    private String orderNumber;
    private String orderLine;
    private String orderTotal;
    private String orderPayment;
    private String OrderTaxDetail;
}