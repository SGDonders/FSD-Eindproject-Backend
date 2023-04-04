package com.fsdeindopdracht.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsdeindopdracht.dtos.inputDto.AccountInputDto;
import com.fsdeindopdracht.dtos.outputDto.AccountOutputDto;
import com.fsdeindopdracht.dtos.registerDto.RegisterDto;
import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.models.Order;
import com.fsdeindopdracht.models.User;
import com.fsdeindopdracht.repositories.AccountRepository;
import com.fsdeindopdracht.repositories.UserRepository;
import com.fsdeindopdracht.services.AccountService;
import com.fsdeindopdracht.services.UserService;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {AccountController.class})
@ExtendWith(SpringExtension.class)
class AccountControllerTest {
    @Autowired
    private AccountController accountController;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;



    @Test
    void testGetAllAccounts() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/accounts");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetAllAccounts2() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/accounts");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }



    @Test
    void testGetAccount() throws Exception {
        when(accountService.getAccount((String) any())).thenReturn(new AccountOutputDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/accounts/{id}", "42");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userName\":null,\"firstName\":null,\"lastName\":null,\"address\":null,\"zipCode\":null,\"phoneNumber\":null,"
                                        + "\"email\":null,\"user\":null}"));
    }



    @Test
    void testGetAccount2() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(new ArrayList<>());
        when(accountService.getAccount((String) any())).thenReturn(new AccountOutputDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/accounts/{id}", "", "Uri Variables");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testUpdateProfile() {

        User user = new User();
        user.setAccount(new Account());
        user.setAuthorities(new HashSet<>());
        user.setOrder(new Order());
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        Account account = new Account();
        account.setAddress("42 Main St");
        account.setEmail("jane.doe@example.org");
        account.setFirstName("Jane");
        account.setLastName("Doe");
        account.setPhoneNumber("4105551212");
        account.setUser(user);
        account.setUserName("janedoe");
        account.setZipCode("21654");

        User user1 = new User();
        user1.setAccount(new Account());
        user1.setAuthorities(new HashSet<>());
        user1.setOrder(new Order());
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");

        Order order = new Order();
        order.setId(123L);
        order.setOrderDate(LocalDate.ofEpochDay(1L));
        order.setOrderTotal(10.0d);
        order.setPickUpDate("2020-03-01");
        order.setProducts(new ArrayList<>());
        order.setTimeFrame(true);
        order.setUser(user1);

        User user2 = new User();
        user2.setAccount(account);
        user2.setAuthorities(new HashSet<>());
        user2.setOrder(order);
        user2.setPassword("iloveyou");
        user2.setUsername("janedoe");

        Account account1 = new Account();
        account1.setAddress("42 Main St");
        account1.setEmail("jane.doe@example.org");
        account1.setFirstName("Jane");
        account1.setLastName("Doe");
        account1.setPhoneNumber("4105551212");
        account1.setUser(user2);
        account1.setUserName("janedoe");
        account1.setZipCode("21654");
        Optional<Account> ofResult = Optional.of(account1);

        Account account2 = new Account();
        account2.setAddress("42 Main St");
        account2.setEmail("jane.doe@example.org");
        account2.setFirstName("Jane");
        account2.setLastName("Doe");
        account2.setPhoneNumber("4105551212");
        account2.setUser(new User());
        account2.setUserName("janedoe");
        account2.setZipCode("21654");

        Order order1 = new Order();
        order1.setId(123L);
        order1.setOrderDate(null);
        order1.setOrderTotal(10.0d);
        order1.setPickUpDate("2020-03-01");
        order1.setProducts(new ArrayList<>());
        order1.setTimeFrame(true);
        order1.setUser(new User());

        User user3 = new User();
        user3.setAccount(account2);
        user3.setAuthorities(new HashSet<>());
        user3.setOrder(order1);
        user3.setPassword("iloveyou");
        user3.setUsername("janedoe");

        Account account3 = new Account();
        account3.setAddress("42 Main St");
        account3.setEmail("jane.doe@example.org");
        account3.setFirstName("Jane");
        account3.setLastName("Doe");
        account3.setPhoneNumber("4105551212");
        account3.setUser(user3);
        account3.setUserName("janedoe");
        account3.setZipCode("21654");

        Account account4 = new Account();
        account4.setAddress("42 Main St");
        account4.setEmail("jane.doe@example.org");
        account4.setFirstName("Jane");
        account4.setLastName("Doe");
        account4.setPhoneNumber("4105551212");
        account4.setUser(new User());
        account4.setUserName("janedoe");
        account4.setZipCode("21654");

        Order order2 = new Order();
        order2.setId(123L);
        order2.setOrderDate(null);
        order2.setOrderTotal(10.0d);
        order2.setPickUpDate("2020-03-01");
        order2.setProducts(new ArrayList<>());
        order2.setTimeFrame(true);
        order2.setUser(new User());

        User user4 = new User();
        user4.setAccount(account4);
        user4.setAuthorities(new HashSet<>());
        user4.setOrder(order2);
        user4.setPassword("iloveyou");
        user4.setUsername("janedoe");

        Order order3 = new Order();
        order3.setId(123L);
        order3.setOrderDate(LocalDate.ofEpochDay(1L));
        order3.setOrderTotal(10.0d);
        order3.setPickUpDate("2020-03-01");
        order3.setProducts(new ArrayList<>());
        order3.setTimeFrame(true);
        order3.setUser(user4);

        User user5 = new User();
        user5.setAccount(account3);
        user5.setAuthorities(new HashSet<>());
        user5.setOrder(order3);
        user5.setPassword("iloveyou");
        user5.setUsername("janedoe");

        Account account5 = new Account();
        account5.setAddress("42 Main St");
        account5.setEmail("jane.doe@example.org");
        account5.setFirstName("Jane");
        account5.setLastName("Doe");
        account5.setPhoneNumber("4105551212");
        account5.setUser(user5);
        account5.setUserName("janedoe");
        account5.setZipCode("21654");
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.save((Account) any())).thenReturn(account5);
        when(accountRepository.findById((String) any())).thenReturn(ofResult);
        UserRepository userRepository = mock(UserRepository.class);
        AccountService accountService = new AccountService(accountRepository, userRepository,
                new Argon2PasswordEncoder());

        AccountController accountController = new AccountController(accountService,
                new UserService(mock(UserRepository.class), mock(AccountRepository.class)));
        ResponseEntity<AccountOutputDto> actualUpdateProfileResult = accountController.updateProfile("42",
                new AccountInputDto());
        assertTrue(actualUpdateProfileResult.hasBody());
        assertTrue(actualUpdateProfileResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUpdateProfileResult.getStatusCode());
        AccountOutputDto body = actualUpdateProfileResult.getBody();
        assertNull(body.getPhoneNumber());
        assertNull(body.getLastName());
        assertNull(body.getFirstName());
        assertNull(body.getEmail());
        assertNull(body.getAddress());
        assertNull(body.getZipCode());
        assertSame(user2, body.getUser());
        assertEquals("janedoe", body.getUserName());
        verify(accountRepository).save((Account) any());
        verify(accountRepository).findById((String) any());
    }
}

