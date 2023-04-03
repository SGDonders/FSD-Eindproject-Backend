package com.fsdeindopdracht.services;

import com.fsdeindopdracht.dtos.inputDto.AccountInputDto;
import com.fsdeindopdracht.dtos.outputDto.AccountOutputDto;
import com.fsdeindopdracht.dtos.registerDto.RegisterDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.models.User;
import com.fsdeindopdracht.repositories.AccountRepository;

import com.fsdeindopdracht.repositories.UserRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AccountServiceTest {


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Mock
    AccountRepository accountRepository;
    @Mock
    UserRepository userRepository;

    AccountService accountService;

    @Captor
    ArgumentCaptor<Account> captor;

    Account testAccount1;
    Account testAccount2;
    Account testAccount3;

    AccountOutputDto accountOutputDto;

    AccountInputDto accountInputDto;

    @BeforeEach
    void setup() {
        accountService = new AccountService(accountRepository, userRepository, passwordEncoder());

        testAccount1 = new Account(
                "SourTangie",
                "Sour",
                "Tangie",
                "1211AT",
                "Celebes 10 Huissen",
                "0612458932",
                "LT.sour@gmail.com"
        );

        testAccount2 = new Account(
                "KevinKostner",
                "Kevin",
                "Kostner",
                "1548BT",
                "Hoofdstraat 456 Drachten",
                "1851269876",
                "KKOstner@gmail.com"
        );

        testAccount3 = new Account(
                "Jessica Alba",
                "Jessica",
                "Alba",
                "1454LT",
                "Goudvink 23 Laren",
                "0678932154",
                "Jessica_A@gmail.com"
        );

        accountInputDto = new AccountInputDto(
                "SourTangie",
                "Sour",
                "Tangie",
                "1211AT",
                "Celebes 10 Huissen",
                "0612458932",
                "Lt.sour@gmail.com", null);
    }

    @Test
    void getAccount() {
        when(accountRepository.findById(testAccount1.getUserName())).thenReturn(Optional.of(testAccount1));
        assertThrows(RecordNotFoundException.class, () -> accountService.getAllAccounts());

        AccountOutputDto accountOutputDto = accountService.getAccount("SourTangie");

        assertEquals("SourTangie", accountOutputDto.getUserName());
        assertEquals("Sour", accountOutputDto.getFirstName());
        assertEquals("Tangie", accountOutputDto.getLastName());
        assertEquals("1211AT", accountOutputDto.getZipCode());
        assertEquals("Celebes 10 Huissen", accountOutputDto.getAddress());
        assertEquals("0612458932", accountOutputDto.getPhoneNumber());
        assertEquals("LT.sour@gmail.com", accountOutputDto.getEmail());
    }

    @Test
    void testGetAllAccounts() {
        List<Account> testAccounts = new ArrayList<>();
        testAccounts.add(testAccount1);
        testAccounts.add(testAccount2);
        testAccounts.add(testAccount3);

        when(accountRepository.findAll()).thenReturn(testAccounts);

        List<AccountOutputDto> accountOutputDtoList = accountService.getAllAccounts();
        assertEquals(3, accountOutputDtoList.size());

        AccountOutputDto accountOutputDto1 = accountOutputDtoList.get(0);
        assertEquals("SourTangie", accountOutputDto1.getUserName());
        assertEquals("Sour", accountOutputDto1.getFirstName());
        assertEquals("Tangie", accountOutputDto1.getLastName());
        assertEquals("1211AT", accountOutputDto1.getZipCode());
        assertEquals("Celebes 10 Huissen", accountOutputDto1.getAddress());
        assertEquals("0612458932", accountOutputDto1.getPhoneNumber());
        assertEquals("LT.sour@gmail.com", accountOutputDto1.getEmail());

        AccountOutputDto accountOutputDto2 = accountOutputDtoList.get(1);
        assertEquals("KevinKostner", accountOutputDto2.getUserName());
        assertEquals("Kevin", accountOutputDto2.getFirstName());
        assertEquals("Kostner", accountOutputDto2.getLastName());
        assertEquals("1548BT", accountOutputDto2.getZipCode());
        assertEquals("Hoofdstraat 456 Drachten", accountOutputDto2.getAddress());
        assertEquals("1851269876", accountOutputDto2.getPhoneNumber());
        assertEquals("KKOstner@gmail.com", accountOutputDto2.getEmail());

        AccountOutputDto accountOutputDto3 = accountOutputDtoList.get(2);
        assertEquals("Jessica Alba", accountOutputDto3.getUserName());
        assertEquals("Jessica", accountOutputDto3.getFirstName());
        assertEquals("Alba", accountOutputDto3.getLastName());
        assertEquals("1454LT", accountOutputDto3.getZipCode());
        assertEquals("Goudvink 23 Laren", accountOutputDto3.getAddress());
        assertEquals("0678932154", accountOutputDto3.getPhoneNumber());
        assertEquals("Jessica_A@gmail.com", accountOutputDto3.getEmail());
    }

    @Test
    void testCreateUserAccount() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUserName("testHailey");
        registerDto.setPassword("testPassword");
        registerDto.setFirstName("Hailey");
        registerDto.setLastName("Bieber Rhodes");
        registerDto.setZipCode("4586 AB");
        registerDto.setAddress("De waard 11");
        registerDto.setPhoneNumber("0678169824");
        registerDto.setEmail("HB_Rhodes@gmail.com");
        registerDto.setUser(new User());

        User newUser = new User();
        newUser.setUsername(registerDto.getUserName());
        newUser.setAccount(null);

        Account newAccount = new Account();
        newAccount.setUserName(registerDto.getUserName());
        newAccount.setFirstName(registerDto.getFirstName());
        newAccount.setLastName(registerDto.getLastName());
        newAccount.setZipCode(registerDto.getZipCode());
        newAccount.setAddress(registerDto.getAddress());
        newAccount.setPhoneNumber(registerDto.getPhoneNumber());
        newAccount.setEmail(registerDto.getEmail());
        newAccount.setUser(registerDto.getUser());

        when(userRepository.findById(registerDto.getUserName())).thenReturn(Optional.of(newUser));
        when(accountRepository.findById(registerDto.getUserName())).thenReturn(Optional.of(newAccount));
        when(userRepository.save(newUser)).thenReturn(newUser);
        when(accountRepository.save(newAccount)).thenReturn(newAccount);

        AccountOutputDto accountOutputDto = accountService.createUserAccount(registerDto);

        assertNotNull(accountOutputDto);
        assertEquals(registerDto.getUserName(), accountOutputDto.getUserName());
        assertEquals(registerDto.getFirstName(), accountOutputDto.getFirstName());
        assertEquals(registerDto.getLastName(), accountOutputDto.getLastName());
        assertEquals(registerDto.getZipCode(), accountOutputDto.getZipCode());
        assertEquals(registerDto.getAddress(), accountOutputDto.getAddress());
        assertEquals(registerDto.getPhoneNumber(), accountOutputDto.getPhoneNumber());
        assertEquals(registerDto.getEmail(), accountOutputDto.getEmail());
    }

    @Test
    void testUpdateProfile() {

        AccountInputDto inputDto = new AccountInputDto();

        Account existingAccount = new Account(
                "Kevin kostner",
                "Kevin",
                "Kostner",
                "12345",
                "123 Main St",
                "0638469247",
                "K_Kostner@gmail.com");

        when(accountRepository.findById(accountInputDto.userName)).thenReturn(Optional.of(existingAccount));

        AccountOutputDto outputDto = accountService.updateProfile(accountInputDto.userName, inputDto);

        verify(accountRepository, times(1)).findById(testAccount1.getUserName());
        verify(accountRepository, times(1)).save(existingAccount);

        assertEquals(inputDto.getFirstName(), existingAccount.getFirstName());
        assertEquals(inputDto.getLastName(), existingAccount.getLastName());
        assertEquals(inputDto.getZipCode(), existingAccount.getZipCode());
        assertEquals(inputDto.getAddress(), existingAccount.getAddress());
        assertEquals(inputDto.getPhoneNumber(), existingAccount.getPhoneNumber());
        assertEquals(inputDto.getEmail(), existingAccount.getEmail());

        assertEquals(outputDto.getFirstName(), existingAccount.getFirstName());
        assertEquals(outputDto.getLastName(), existingAccount.getLastName());
        assertEquals(outputDto.getZipCode(), existingAccount.getZipCode());
        assertEquals(outputDto.getAddress(), existingAccount.getAddress());
        assertEquals(outputDto.getPhoneNumber(), existingAccount.getPhoneNumber());
        assertEquals(outputDto.getEmail(), existingAccount.getEmail());
    }


}