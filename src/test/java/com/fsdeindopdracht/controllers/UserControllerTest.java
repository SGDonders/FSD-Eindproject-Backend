package com.fsdeindopdracht.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsdeindopdracht.dtos.registerDto.RegisterDto;
import com.fsdeindopdracht.execeptions.UsernameNotFoundException;
import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.models.User;
import com.fsdeindopdracht.services.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Test
    void testAddUserAuthority() throws Exception {
        doNothing().when(userService).addAuthority((String) any(), (String) any());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .post("/users/{username}/authorities", "janedoe")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new HashMap<>()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void testDeleteUserAuthority() throws Exception {
        doNothing().when(userService).removeAuthority((String) any(), (String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/users/{username}/authorities/{authority}", "janedoe", "JaneDoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void testGetUserAuthorities2() throws Exception {
        when(userService.getUser((String) any())).thenReturn(new RegisterDto());
        when(userService.getAuthorities((String) any())).thenThrow(new UsernameNotFoundException("janedoe"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{username}/authorities", "",
                "Uri Variables");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userName\":null,\"password\":null,\"firstName\":null,\"lastName\":null,\"address\":null,\"zipCode\":null,"
                                        + "\"phoneNumber\":null,\"email\":null,\"account\":null,\"user\":null,\"authorities\":null}"));
    }


    @Test
    void testGetUserAuthorities3() throws Exception {
        Account account = new Account();
        User user = new User();
        when(userService.getUser((String) any())).thenReturn(new RegisterDto("janedoe", "iloveyou", "Jane", "Doe",
                "42 Main St", "21654", "4105551212", "jane.doe@example.org", account, user, new HashSet<>()));
        when(userService.getAuthorities((String) any())).thenThrow(new UsernameNotFoundException("janedoe"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{username}/authorities", "",
                "Uri Variables");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userName\":\"janedoe\",\"password\":\"iloveyou\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"address\":\"42 Main"
                                        + " St\",\"zipCode\":\"21654\",\"phoneNumber\":\"4105551212\",\"email\":\"jane.doe@example.org\",\"account\":{\"userName"
                                        + "\":null,\"firstName\":null,\"lastName\":null,\"zipCode\":null,\"phoneNumber\":null,\"email\":null,\"address\":null"
                                        + "},\"user\":{\"username\":null,\"password\":null,\"authorities\":[],\"order\":null},\"authorities\":[]}"));
    }


    @Test
    void testGetUserAuthorities4() throws Exception {
        when(userService.getUser((String) any())).thenThrow(new UsernameNotFoundException("janedoe"));
        when(userService.getAuthorities((String) any())).thenThrow(new UsernameNotFoundException("janedoe"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{username}/authorities", "",
                "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void testDeleteKlant() throws Exception {
        doNothing().when(userService).deleteUser((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{username}", "janedoe");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User successful deleted"));
    }


    @Test
    void testGetUserAuthorities() throws Exception {
        when(userService.getAuthorities((String) any())).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{username}/authorities",
                "janedoe");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetUsers() throws Exception {
        when(userService.getUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


}

