package com.fsdeindopdracht.services;

import com.fsdeindopdracht.dtos.outputDto.AccountOutputDto;
import com.fsdeindopdracht.dtos.outputDto.OrderOutputDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.models.Order;
import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.models.User;
import com.fsdeindopdracht.repositories.OrderRepository;
import com.fsdeindopdracht.repositories.ProductRepository;
import com.fsdeindopdracht.repositories.UserRepository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = {OrderService.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {


    @Mock
    OrderRepository orderRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ProductRepository productRepository;

    OrderService orderService;

    @Captor
    ArgumentCaptor<Account> captor;

    Order order1;
    Order order2;
    User user1;
    User user2;
    Product product1;
    Product product2;
    AccountOutputDto accountOutputDto;

    @BeforeEach
    void setup() {
        orderService = new OrderService(orderRepository, productRepository, userRepository);

        user1 = new User(
                "Kevin kostner",
                "password1",
                null,
                null,
                null
        );

        user2 = new User(
                "Michael jackson",
                "password2",
                null,
                null,
                null
        );

        product1 = new Product(
                1L,
                "apple",
                5.0,
                115.0,
                "fruit",
                null,
                null
        );

        product2 = new Product(
                2L,
                "carrot",
                2.55,
                45.0,
                "fruit",
                null,
                null
        );

        ArrayList<Product> arrayList1 = new ArrayList<>();

        arrayList1.add(product1);
        arrayList1.add(product2);

        order1 = new Order(
                1L,
                123.0, LocalDate.now(),
                "25-03-2023",
                true,
                arrayList1,
                user1
        );

        order2 = new Order(
                2L,
                80.0, LocalDate.now(),
                "05-04-2023",
                true,
                arrayList1,
                user1
        );
    }

    @Test
    public void testGetOrder() {

        Mockito.when(orderRepository.findById(order1.getId())).thenReturn(Optional.of(order1));

        OrderOutputDto result = orderService.getOrder(order1.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(order1.getId(), result.getId());
    }

    @Test
    public void testGetOrderNotFound() {

        Mockito.when(orderRepository.findById(order1.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            orderService.getOrder(order1.getId());
        });
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        order1.setId(1L);
        orders.add(order1);

        order2.setId(2L);
        orders.add(order2);

        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        List<OrderOutputDto> result = orderService.getAllOrders();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(order1.getId(), result.get(0).getId());
        Assertions.assertEquals(order2.getId(), result.get(1).getId());
    }


    @Test
    public void testGetAllOrdersNotFound() {
        Mockito.when(orderRepository.findAll()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            orderService.getAllOrders();
        });
    }





}


