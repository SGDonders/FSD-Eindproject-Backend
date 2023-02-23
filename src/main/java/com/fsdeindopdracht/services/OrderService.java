package com.fsdeindopdracht.services;

import com.fsdeindopdracht.dtos.inputDto.OrderInputDto;
import com.fsdeindopdracht.dtos.outputDto.OrderOutputDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Order;
import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.models.User;
import com.fsdeindopdracht.repositories.OrderRepository;
import com.fsdeindopdracht.repositories.ProductRepository;
import com.fsdeindopdracht.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static OrderRepository orderRepository;
    private static ProductRepository productRepository;

    private static UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Functie voor GetMapping van alle Orders.
    public List<OrderOutputDto> getAllOrders() {
        List<Order> optionalOrder = orderRepository.findAll();
        List<OrderOutputDto> orderOutputDtoList = new ArrayList<>();

        if (optionalOrder.isEmpty()) {
            throw new RecordNotFoundException("Order not found!");
        } else {
            for (Order order : optionalOrder) {
                OrderOutputDto orderOutputDto = transferOrderToOutputDto(order);

                orderOutputDtoList.add(orderOutputDto);

            }
        }
        return orderOutputDtoList;
    }

    // Functie voor getMapping één Order.
    public OrderOutputDto getOrder(Long id) {
        Optional<Order> requestedOrder = orderRepository.findById(id);
        if (requestedOrder.isEmpty()) {
            throw new RecordNotFoundException("Order not found!");
        } else {
            return transferOrderToOutputDto(requestedOrder.get());
        }
    }

    // Functie voor deleteMapping.
    public void deleteOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new RecordNotFoundException("Order already removed or doesn't exist!");
        } else {
            Order orderObj = optionalOrder.get();
            orderRepository.delete(orderObj);
        }
    }

    // Functie voor PostMapping
    public Order createOrder(OrderInputDto orderInputDto) {

        User user;

        Optional<User> optionalUser = userRepository.findById(orderInputDto.getUserName());
        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User already removed or doesn't exist!");
        } else {
            user = optionalUser.get();
        }

        List<Product> listOfProducts = new ArrayList<>();
        for( String productName : orderInputDto.getProductNames()){
            Optional<Product> optionalProduct = productRepository.findByProductName(productName);

            if(optionalProduct.isEmpty()) {
                throw new RecordNotFoundException("No products with this name");
            }else {
                Product product = optionalProduct.get();
                listOfProducts.add(product);
            }
        }

        Order newOrder = transferInputDtoToOrder(orderInputDto);
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setProducts(listOfProducts);
        newOrder.setUser(user);

        orderRepository.save(newOrder);


        return newOrder;
    }



    // Functie voor PatchMapping.
    public OrderOutputDto updateOrder(Long id, OrderInputDto orderInputDto) {

        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {

            Order orderUpdate = optionalOrder.get();

            if (orderInputDto.getProductNames() != null) {
                List<Product> listOfProducts = new ArrayList<>();
                for( String productName : orderInputDto.getProductNames()){
                    Optional<Product> optionalProduct = productRepository.findByProductName(productName);

                    if(optionalProduct.isEmpty()) {
                        throw new RecordNotFoundException("No products");
                    }else {
                        Product product = optionalProduct.get();
                        listOfProducts.add(product);
                    }
                }
                orderUpdate.setProducts(listOfProducts);
            }

            if (orderInputDto.getOrderTotal() != null) {
                orderUpdate.setOrderTotal(orderInputDto.getOrderTotal());
            }

            Order updatedOrder = orderRepository.save(orderUpdate);
            return transferOrderToOutputDto(updatedOrder);
        } else {
            throw new RecordNotFoundException("Order not found!");

        }
    }

    public static void assignOrderToUser(Long id, String userid) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(userid);
        if (optionalOrder.isPresent() && optionalUser.isPresent()) {
            Order order = optionalOrder.get();
            User user = optionalUser.get();
            user.setOrder(order);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException();
        }
    }


    // Wrapper functie
    public Order transferInputDtoToOrder(OrderInputDto orderInputDto) {

        Order newOrder = new Order();

        newOrder.setOrderTotal(orderInputDto.getOrderTotal());
        newOrder.setPickUpDate(orderInputDto.getPickUpDate());
        newOrder.setTimeFrame(orderInputDto.getTimeFrame());


        return newOrder;
    }

    // Wrapper Functie
    public OrderOutputDto transferOrderToOutputDto(Order order) {

        OrderOutputDto orderOutputDto = new OrderOutputDto();
        orderOutputDto.setProductNames(order.getProducts());
        orderOutputDto.setPickUpDate(order.getPickUpDate());
        orderOutputDto.setTimeFrame(order.getTimeFrame());

        return orderOutputDto;
    }


}