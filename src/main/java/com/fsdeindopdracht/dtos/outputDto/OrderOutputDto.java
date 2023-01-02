package com.fsdeindopdracht.dtos.outputDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderOutputDto {
    public Long id;
    public String orderHeader;
    public String comment;
    public String orderNumber;
    public String orderLine;
    public String orderTotal;
    public String orderPayment;
    public String OrderTaxDetail;
}