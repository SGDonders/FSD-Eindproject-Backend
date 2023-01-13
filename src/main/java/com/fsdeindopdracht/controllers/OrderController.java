package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.dtos.inputDto.OrderInputDto;
import com.fsdeindopdracht.dtos.outputDto.OrderOutputDto;
import com.fsdeindopdracht.models.Order;
import com.fsdeindopdracht.services.OrderService;
import com.fsdeindopdracht.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    // GetMapping request alle Orders.
    @GetMapping("")
    public ResponseEntity<List<OrderOutputDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    // GetMapping request voor één Order.
    @GetMapping("{id}")
    public ResponseEntity<OrderOutputDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }


    // DeleteMapping request voor een Order.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id); //
        return ResponseEntity.noContent().build();
    }


    // PostMapping request voor een Order.
    @PostMapping("")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderInputDto orderInputDto,
                                                 BindingResult bindingResult) throws ValidationException {
        Utils.reportErrors(bindingResult);

        Order savedOrder = orderService.createOrder(orderInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath
                ().path("/Order/" + savedOrder.getId()).toUriString());

        return ResponseEntity.created(uri).body(savedOrder);
    }


    // PutMapping voor een Order.
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody OrderInputDto orderInputDto) {
        OrderOutputDto orderOutputDto = orderService.updateOrder(id, orderInputDto);
        return ResponseEntity.ok().body(orderOutputDto);
    }
}

