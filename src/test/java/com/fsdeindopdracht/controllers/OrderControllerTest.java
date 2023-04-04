package com.fsdeindopdracht.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsdeindopdracht.dtos.inputDto.OrderInputDto;
import com.fsdeindopdracht.dtos.outputDto.OrderOutputDto;
import com.fsdeindopdracht.services.OrderService;
import com.fsdeindopdracht.services.UserService;

import java.time.LocalDate;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
class OrderControllerTest {
    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;


    @Test
    void testGetAllOrder() throws Exception {
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders");
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetOrder() throws Exception {
        when(orderService.getOrder((Long) any())).thenReturn(new OrderOutputDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/{id}", 123L);
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"userName\":null,\"orderTotal\":null,\"orderDate\":null,\"pickUpDate\":null,\"timeFrame\":null,"
                                        + "\"productNames\":null}"));
    }


    @Test
    void testGetOrder2() throws Exception {
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());
        when(orderService.getOrder((Long) any())).thenReturn(new OrderOutputDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/{id}", "", "Uri Variables");
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }



    @Test
    void testCreateOrder2() throws Exception {
        when(orderService.createOrder((OrderInputDto) any())).thenReturn(new OrderOutputDto());

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add(0,"lettuce");
        stringList.add(1,"carrot");

        OrderInputDto orderInputDto = new OrderInputDto();
        orderInputDto.setId(123L);
        orderInputDto.setOrderDate(null);
        orderInputDto.setOrderTotal(2.0d);
        orderInputDto.setPickUpDate("2020-03-01");
        orderInputDto.setProductNames(stringList);
        orderInputDto.setTimeFrame(true);
        orderInputDto.setUserName("Kevin Kostner");
        String content = (new ObjectMapper()).writeValueAsString(orderInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"userName\":null,\"orderTotal\":null,\"orderDate\":null,\"pickUpDate\":null,\"timeFrame\":null,"
                                        + "\"productNames\":null}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/Order/null"));
    }


    @Test
    void testDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/orders/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void testDeleteOrder2() throws Exception {
        doNothing().when(orderService).deleteOrder((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/orders/{id}", 123L);
        deleteResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void testGetAllOrder2() throws Exception {
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/orders");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

