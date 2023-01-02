package com.fsdeindopdracht.services;

import com.fsdeindopdracht.dtos.inputDto.OrderInputDto;
import com.fsdeindopdracht.dtos.outputDto.OrderOutputDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Order;
import com.fsdeindopdracht.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    // Wrapper functie
    public Order transferInputDtoToOrder(OrderInputDto orderInputDto) {

        Order newOrder = new Order();

        newOrder.setOrderHeader(orderInputDto.getOrderHeader());
        newOrder.setComment(orderInputDto.getComment());
        newOrder.setOrderNumber(orderInputDto.getOrderNumber());
        newOrder.setOrderLine(orderInputDto.getOrderLine());
        newOrder.setOrderTotal(orderInputDto.getOrderTotal());
        newOrder.setOrderPayment(orderInputDto.getOrderPayment());
        newOrder.setOrderTaxDetail(orderInputDto.getOrderTaxDetail());


        return orderRepository.save(newOrder);
    }

    // Wrapper Functie
    public OrderOutputDto transferOrderToOutputDto(Order order) {

        OrderOutputDto orderOutputDto = new OrderOutputDto();

        orderOutputDto.setOrderHeader(order.getOrderHeader());
        orderOutputDto.setComment(order.getComment());
        orderOutputDto.setOrderNumber(order.getOrderNumber());
        orderOutputDto.setOrderLine(order.getOrderLine());
        orderOutputDto.setOrderTotal(order.getOrderTotal());
        orderOutputDto.setOrderPayment(order.getOrderPayment());
        orderOutputDto.setOrderTaxDetail(order.getOrderTaxDetail());

        return orderOutputDto;
    }

    // Functie voor GetMapping van alle Orders.
    public List<OrderOutputDto> getAllOrders() {
        List<Order> optionalOrder = orderRepository.findAll();
        List<OrderOutputDto> OrderOutputDtoList = new ArrayList<>();

        if (optionalOrder.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            for (Order order : optionalOrder) {
                OrderOutputDto orderOutputDto = transferOrderToOutputDto(order);

                OrderOutputDtoList.add(orderOutputDto);

            }
        }
        return OrderOutputDtoList;
    }

    // Functie voor getMapping één Order.
    public OrderOutputDto getOrder(Long id) {
        Optional<Order> requestedOrder = orderRepository.findById(id);
        if (requestedOrder.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            return transferOrderToOutputDto(requestedOrder.get());
        }
    }

    // Functie voor deleteMapping.
    public void deleteOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        // optional tv.
        if (optionalOrder.isEmpty()) {
            throw new RecordNotFoundException("Television already removed or doesn't exist!");
            // throw exception.
        } else {
            Order OrderObj = optionalOrder.get();
            // er een tv van.
            orderRepository.delete(OrderObj);
        }
    }

    // Functie voor PostMapping
    public Order createOrder(OrderInputDto orderInputDto) {
        Order newOrder = transferInputDtoToOrder(orderInputDto);
        return orderRepository.save(newOrder);
    }


    // Functie voor PutMapping.
    public OrderOutputDto updateOrder(Long id, OrderInputDto orderInputDto) {

        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {

            Order orderUpdate = optionalOrder.get();

            if (orderInputDto.getOrderHeader() != null) {
                orderUpdate.setOrderHeader(orderInputDto.getOrderHeader());
            }
            if (orderInputDto.getComment() != null) {
                orderUpdate.setComment(orderInputDto.getComment());
            }
            if (orderInputDto.getOrderNumber() != null) {
                orderUpdate.setOrderNumber(orderInputDto.getOrderNumber());
            }
            if (orderInputDto.getOrderLine() != null) {
                orderUpdate.setOrderLine(orderInputDto.getOrderLine());
            }
            if (orderInputDto.getOrderTotal() != null) {
                orderUpdate.setOrderTotal(orderInputDto.getOrderTotal());
            }
            if (orderInputDto.getOrderPayment() != null) {
                orderUpdate.setOrderPayment(orderInputDto.getOrderPayment());
            }
            if (orderInputDto.getOrderTaxDetail() != null) {
                orderUpdate.setOrderTaxDetail(orderInputDto.getOrderTaxDetail());
            }
            Order updatedOrder = orderRepository.save(orderUpdate);
            return transferOrderToOutputDto(updatedOrder);
        } else {
            throw new RecordNotFoundException("Order not found!");

        }
    }
}