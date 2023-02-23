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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // Function for getMapping one account.
    public AccountOutputDto getAccount(String id) {
        Optional<Account> requestedAccount = accountRepository.findById(id);

        if (requestedAccount.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            return transferAccountToOutputDto(requestedAccount.get());
        }
    }

    // Function for getMapping all accounts.
    public List<AccountOutputDto> getAllAccounts() {
        List<Account> optionalAccount = accountRepository.findAll();
        List<AccountOutputDto> allAccountsOutputDtoList = new ArrayList<>();

        if (optionalAccount.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            for (Account allAccounts : optionalAccount) {
                AccountOutputDto allAccountsOutputDto = transferAccountToOutputDto(allAccounts);
                allAccountsOutputDtoList.add(allAccountsOutputDto);
            }
        }
        return allAccountsOutputDtoList;
    }

    // Function for deleteMapping one account.
    public void deleteAccount(String id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isEmpty()) {
            throw new RecordNotFoundException("Television already removed or doesn't exist!");
        } else {
            Account accountObj = optionalAccount.get();
            accountRepository.delete(accountObj);
        }
    }

    // Function for postMapping user/account.
    // (This function stores an user and account at the same time with an oneToOne relation.)
    public AccountOutputDto createUserAccount(RegisterDto registerDto) {

        User newUser = new User();

        newUser.setUsername(registerDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Account newAccount = new Account();

        newAccount.setUserName(registerDto.getUsername());
        newAccount.setFirstName(registerDto.getFirstName());
        newAccount.setLastName(registerDto.getLastName());
        newAccount.setZipCode(registerDto.getZipCode());
        newAccount.setAddress(registerDto.getAddress());
        newAccount.setPhoneNumber(registerDto.getPhoneNumber());
        newAccount.setEmail(registerDto.getEmail());
        newAccount.setUser(registerDto.getUser());

        userRepository.save(newUser);
        accountRepository.save(newAccount);
        newUser.setAccount(newAccount);

        assignAccountToUser(newUser.getUsername(), newAccount.getUserName());

        accountRepository.save(newAccount);
        userRepository.save(newUser);

        return transferAccountToOutputDto(newAccount);
    }

    // Function for putMapping.
    public AccountOutputDto updateProfile( String id, AccountInputDto accountInputDto) {
        Optional<Account> updateProfile = accountRepository.findById(id);
        if (updateProfile.isPresent()) {

            Account newAccount = updateProfile.get();

            newAccount.setFirstName(accountInputDto.getFirstName());
            newAccount.setLastName(accountInputDto.getLastName());
            newAccount.setZipCode(accountInputDto.getZipCode());
            newAccount.setAddress(accountInputDto.getAddress());
            newAccount.setPhoneNumber(accountInputDto.getPhoneNumber());
            newAccount.setEmail(accountInputDto.getEmail());

            accountRepository.save(newAccount);

            return transferAccountToOutputDto(newAccount);
        }else {
            throw new RecordNotFoundException("Account with " + id + " not updated");
        }
    }

    // Function for patchMapping.
    public AccountOutputDto updateAccount(String id, AccountInputDto accountInputDto) {
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

    // Function to assign account to an user.
    public void assignAccountToUser(String id, String accountId) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalUser.isPresent() && optionalAccount.isPresent()) {
            User user = optionalUser.get();
            Account account = optionalAccount.get();
            account.setUser(user);
            accountRepository.save(account);
        } else {
            throw new RecordNotFoundException();

        }
    }


// Transfer function dto/account----------------------------------------------------------------------------------------

    public Account transferInputDtoToAccount(AccountInputDto accountInputDto) {

        Account newAccount = new Account();

        newAccount.setUserName(accountInputDto.userName);
        newAccount.setFirstName(accountInputDto.firstName);
        newAccount.setLastName(accountInputDto.lastName);
        newAccount.setAddress(accountInputDto.address);
        newAccount.setZipCode(accountInputDto.zipCode);
        newAccount.setPhoneNumber(accountInputDto.phoneNumber);
        newAccount.setEmail(accountInputDto.email);

        return newAccount;
    }

    public AccountOutputDto transferAccountToOutputDto(Account account) {

        AccountOutputDto accountOutputDto = new AccountOutputDto();

        accountOutputDto.setUserName(account.getUserName());
        accountOutputDto.setFirstName(account.getFirstName());
        accountOutputDto.setLastName(account.getLastName());
        accountOutputDto.setAddress(account.getAddress());
        accountOutputDto.setZipCode(account.getZipCode());
        accountOutputDto.setPhoneNumber(account.getPhoneNumber());
        accountOutputDto.setEmail(account.getEmail());
        accountOutputDto.setUser(account.getUser());

        return accountOutputDto;
    }



}
