package com.fsdeindopdracht.services;

import com.fsdeindopdracht.dtos.inputDto.AccountInputDto;
import com.fsdeindopdracht.dtos.outputDto.AccountOutputDto;
import com.fsdeindopdracht.dtos.registerDto.RegisterDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.models.User;
import com.fsdeindopdracht.repositories.AccountRepository;
import com.fsdeindopdracht.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired // erin gezet voor de functie createUserAccount
    @Lazy
    private PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // Wrapper functie.
    public Account transferInputDtoToAccount(AccountInputDto accountInputDto) {

        Account newAccount = new Account();

        newAccount.setFirstName(accountInputDto.firstName);
        newAccount.setLastName(accountInputDto.lastName);
        newAccount.setAddress(accountInputDto.address);
        newAccount.setZipCode(accountInputDto.zipCode);
        newAccount.setPhoneNumber(accountInputDto.phoneNumber);
        newAccount.setEmail(accountInputDto.email);

        return accountRepository.save(newAccount);
    }

    // Wrapper functie.
    public AccountOutputDto transferAccountToOutputDto(Account account) {

        AccountOutputDto accountOutputDto = new AccountOutputDto();

        accountOutputDto.setFirstName(account.getFirstName());
        accountOutputDto.setLastName(account.getLastName());
        accountOutputDto.setAdress(account.getAddress());
        accountOutputDto.setZipCode(account.getZipCode());
        accountOutputDto.setPhoneNumber(account.getPhoneNumber());
        accountOutputDto.setEmail(account.getEmail());

        return accountOutputDto;
    }

    // Functie voor GetMapping.
    public AccountOutputDto getAccount(Long id) {
        Optional<Account> requestedAccount = accountRepository.findById(id);

        if (requestedAccount.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            return transferAccountToOutputDto(requestedAccount.get());
        }
    }

    // Functie voor GetMapping.
    public List<AccountOutputDto> getAllAccounts() {
        List<Account> optionalAccount = accountRepository.findAll();
        List<AccountOutputDto> wallBracketOutputDtoList = new ArrayList<>();

        if (optionalAccount.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            for (Account wallBracket : optionalAccount) {
                AccountOutputDto wallBracketOutputDto = transferAccountToOutputDto(wallBracket);
                wallBracketOutputDtoList.add(wallBracketOutputDto);
            }
        }
        return wallBracketOutputDtoList;
    }

    //Functie voor deleteMapping.
    public void deleteAccount(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isEmpty()) {
            throw new RecordNotFoundException("Television already removed or doesn't exist!");
        } else {
            Account accountObj = optionalAccount.get();
            accountRepository.delete(accountObj);
        }
    }

    //Functie voor PostMapping.
    public AccountOutputDto createUserAccount(RegisterDto registerDto) {

        User newUser = new User();

        newUser.setUsername(registerDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(newUser);

        Account newAccount = new Account();

        newAccount.setFirstName(registerDto.getFirstName());
        newAccount.setLastName(registerDto.getLastName());
        newAccount.setZipCode(registerDto.getZipCode());
        newAccount.setAddress(registerDto.getAddress());
        newAccount.setPhoneNumber(registerDto.getPhoneNumber());
        newAccount.setEmail(registerDto.getEmail());

        accountRepository.save(newAccount);

        newAccount.setUser(newUser);
        newUser.setAccount(newAccount);

        accountRepository.save(newAccount);
        userRepository.save(newUser);

        return transferAccountToOutputDto(newAccount);
    }

    // Functie voor patchMapping.
    public AccountOutputDto updateAccount(Long id, AccountInputDto accountInputDto) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isPresent()) {

            Account accountUpdate = optionalAccount.get();


            if (accountInputDto.getFirstName() != null) {
                accountUpdate.setFirstName(accountInputDto.getFirstName());
            }
            if (accountInputDto.getLastName() != null) {
                accountUpdate.setLastName(accountInputDto.getLastName());
            }
            if (accountInputDto.getAddress() != null) {
                accountUpdate.setAddress(accountInputDto.getAddress());
            }
            if (accountInputDto.getZipCode() != null) {
                accountUpdate.setZipCode(accountInputDto.getZipCode());
            }
            if (accountInputDto.getPhoneNumber() != null) {
                accountUpdate.setPhoneNumber(accountInputDto.getPhoneNumber());
            }
            if (accountInputDto.getEmail() != null) {
                accountUpdate.setEmail(accountInputDto.getEmail());
            }

            Account updatedAccount = accountRepository.save(accountUpdate);
            return transferAccountToOutputDto(updatedAccount);
        } else {
            throw new RecordNotFoundException("Account not found!");
        }
    }


}
