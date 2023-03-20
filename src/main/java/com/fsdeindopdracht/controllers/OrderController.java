package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.dtos.inputDto.OrderInputDto;
import com.fsdeindopdracht.dtos.outputDto.OrderOutputDto;
import com.fsdeindopdracht.models.Order;
import com.fsdeindopdracht.services.OrderService;
import com.fsdeindopdracht.services.UserService;
import com.fsdeindopdracht.utils.Utils;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    // GetMapping request for all orders.
    @GetMapping("")
    public ResponseEntity<List<OrderOutputDto>> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    // GetMapping request for an order.
    @GetMapping("{id}")
    public ResponseEntity<OrderOutputDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }


    // PostMapping request for an order.
    @PostMapping("")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderInputDto orderInputDto,
                                              BindingResult bindingResult) throws ValidationException {
        Utils.reportErrors(bindingResult);
        OrderOutputDto savedOrder = orderService.createOrder(orderInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/Order/" + savedOrder.getId()).toUriString());

        return ResponseEntity.created(uri).body(savedOrder);
    }


    // PutMapping for an Order.
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody OrderInputDto orderInputDto) {
        OrderOutputDto invoiceOutputDto = orderService.updateOrder(id, orderInputDto);
        return ResponseEntity.ok().body(invoiceOutputDto);
    }


    // DeleteMapping request for an Order.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id); //
        return ResponseEntity.noContent().build();
    }
}

