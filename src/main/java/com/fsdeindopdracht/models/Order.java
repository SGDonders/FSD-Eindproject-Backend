package com.fsdeindopdracht.models;

import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String orderHeader;
    private String comment;
    private String orderNumber;
    private String orderLine;
    private String orderTotal;
    private String orderPayment;
    private String OrderTaxDetail;
}
