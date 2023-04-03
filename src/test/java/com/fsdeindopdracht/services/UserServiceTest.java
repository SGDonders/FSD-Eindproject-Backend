package com.fsdeindopdracht.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.models.Authority;
import com.fsdeindopdracht.models.Order;
import com.fsdeindopdracht.models.User;
import com.fsdeindopdracht.repositories.AccountRepository;
import com.fsdeindopdracht.repositories.UserRepository;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
@PropertySource("classpath:application-test.properties")
@EnableConfigurationProperties
class UserServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    void testGetUsers() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(userService.getUsers().isEmpty());
        verify(userRepository).findAll();
    }


    @Test
    void testGetUsers2() {
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setEmail("jane.doe@example.org");
        account.setFirstName("Jane");
        account.setLastName("Doe");
        account.setPhoneNumber("4105551212");
        account.setUser(new User());
        account.setUserName("janedoe");
        account.setZipCode("21654");

        User user = new User();
        user.setAccount(account);
        user.setAuthorities(new HashSet<>());
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        Account account1 = new Account();
        account1.setAddress("42 Main St");
        account1.setEmail("jane.doe@example.org");
        account1.setFirstName("Jane");
        account1.setLastName("Doe");
        account1.setPhoneNumber("4105551212");
        account1.setUser(user);
        account1.setUserName("janedoe");
        account1.setZipCode("21654");

        Account account2 = new Account();
        account2.setAddress("42 Main St");
        account2.setEmail("jane.doe@example.org");
        account2.setFirstName("Jane");
        account2.setLastName("Doe");
        account2.setPhoneNumber("4105551212");
        account2.setUser(new User());
        account2.setUserName("janedoe");
        account2.setZipCode("21654");

        User user1 = new User();
        user1.setAccount(account2);
        user1.setAuthorities(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");

        User user2 = new User();
        user2.setAccount(account1);
        user2.setAuthorities(new HashSet<>());
        user2.setPassword("iloveyou");
        user2.setUsername("janedoe");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(1, userService.getUsers().size());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUsers3() {
        when(userRepository.findAll()).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> userService.getUsers());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUsers4() {
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setEmail("jane.doe@example.org");
        account.setFirstName("Jane");
        account.setLastName("Doe");
        account.setPhoneNumber("4105551212");
        account.setUser(new User());
        account.setUserName("janedoe");
        account.setZipCode("21654");

        User user = new User();
        user.setAccount(account);
        user.setAuthorities(new HashSet<>());
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        Account account1 = new Account();
        account1.setAddress("42 Main St");
        account1.setEmail("jane.doe@example.org");
        account1.setFirstName("Jane");
        account1.setLastName("Doe");
        account1.setPhoneNumber("4105551212");
        account1.setUser(user);
        account1.setUserName("janedoe");
        account1.setZipCode("21654");

        Account account2 = new Account();
        account2.setAddress("42 Main St");
        account2.setEmail("jane.doe@example.org");
        account2.setFirstName("Jane");
        account2.setLastName("Doe");
        account2.setPhoneNumber("4105551212");
        account2.setUser(new User());
        account2.setUserName("janedoe");
        account2.setZipCode("21654");

        User user1 = new User();
        user1.setAccount(account2);
        user1.setAuthorities(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");

        Account account3 = new Account();
        account3.setAddress("42 Main St");
        account3.setEmail("jane.doe@example.org");
        account3.setFirstName("Jane");
        account3.setLastName("Doe");
        account3.setPhoneNumber("4105551212");
        account3.setUser(new User());
        account3.setUserName("janedoe");
        account3.setZipCode("21654");


        User user2 = new User();
        user2.setAccount(account3);
        user2.setAuthorities(new HashSet<>());
        user2.setPassword("iloveyou");
        user2.setUsername("janedoe");

        Account account4 = new Account();
        account4.setAddress("42 Main St");
        account4.setEmail("jane.doe@example.org");
        account4.setFirstName("Jane");
        account4.setLastName("Doe");
        account4.setPhoneNumber("4105551212");
        account4.setUser(user2);
        account4.setUserName("janedoe");
        account4.setZipCode("21654");

        Account account5 = new Account();
        account5.setAddress("42 Main St");
        account5.setEmail("jane.doe@example.org");
        account5.setFirstName("Jane");
        account5.setLastName("Doe");
        account5.setPhoneNumber("4105551212");
        account5.setUser(new User());
        account5.setUserName("janedoe");
        account5.setZipCode("21654");

        User user3 = new User();
        user3.setAccount(account5);
        user3.setAuthorities(new HashSet<>());
        user3.setPassword("iloveyou");
        user3.setUsername("janedoe");


        User user4 = new User();
        user4.setAccount(account4);
        user4.setAuthorities(new HashSet<>());
        user4.setPassword("iloveyou");
        user4.setUsername("janedoe");

        Account account6 = new Account();
        account6.setAddress("42 Main St");
        account6.setEmail("jane.doe@example.org");
        account6.setFirstName("Jane");
        account6.setLastName("Doe");
        account6.setPhoneNumber("4105551212");
        account6.setUser(user4);
        account6.setUserName("janedoe");
        account6.setZipCode("21654");
        User user5 = mock(User.class);
        when(user5.getAccount()).thenReturn(account6);
        when(user5.getPassword()).thenReturn("iloveyou");
        when(user5.getUsername()).thenReturn("janedoe");
        when(user5.getAuthorities()).thenReturn(new HashSet<>());
        doNothing().when(user5).setAccount(any());
        doNothing().when(user5).setAuthorities(any());
        doNothing().when(user5).setPassword(any());
        doNothing().when(user5).setUsername(any());
        user5.setAccount(account1);
        user5.setAuthorities(new HashSet<>());
        user5.setPassword("iloveyou");
        user5.setUsername("janedoe");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user5);
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(1, userService.getUsers().size());
        verify(userRepository).findAll();
        verify(user5).getAccount();
        verify(user5).getPassword();
        verify(user5).getUsername();
        verify(user5).getAuthorities();
        verify(user5).setAccount(any());
        verify(user5).setAuthorities(any());
        verify(user5).setPassword(any());
        verify(user5).setUsername(any());
    }






}

