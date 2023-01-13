package com.fsdeindopdracht.services;

import com.fsdeindopdracht.dtos.inputDto.AccountInputDto;
import com.fsdeindopdracht.dtos.outputDto.AccountOutputDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Wrapper functie.
    public Account transferInputDtoToAccount(AccountInputDto accountInputDto) {

        Account newAccount = new Account();

        newAccount.setUsername(accountInputDto.username);
        newAccount.setPassword(accountInputDto.password);
        newAccount.setFirstName(accountInputDto.firstName);
        newAccount.setLastName(accountInputDto.lastName);
        newAccount.setAdress(accountInputDto.adress);
        newAccount.setZipCode(accountInputDto.zipCode);
        newAccount.setPhoneNumber(accountInputDto.phoneNumber);
        newAccount.setEmail(accountInputDto.email);
        newAccount.setEnabled(accountInputDto.enabled);

        return accountRepository.save(newAccount);
    }

    // Wrapper functie.
    public AccountOutputDto transferAccountToOutputDto(Account account) {

        AccountOutputDto accountOutputDto = new AccountOutputDto();

        accountOutputDto.setUsername(account.getUsername());
        accountOutputDto.setPassword(account.getPassword());
        accountOutputDto.setFirstName(account.getFirstName());
        accountOutputDto.setLastName(account.getLastName());
        accountOutputDto.setAdress(account.getAdress());
        accountOutputDto.setZipCode(account.getZipCode());
        accountOutputDto.setPhoneNumber(account.getPhoneNumber());
        accountOutputDto.setEmail(account.getEmail());
        accountOutputDto.setEnabled(account.isEnabled());

        return accountOutputDto;
    }

    // Functie voor GetMapping.
    public AccountOutputDto getAccount(Long id) {
        Optional<Account> requestedAccount = accountRepository.findById(getAccount(id).getId()); //original input id

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

    // Functie voor PostMapping.
    public Account createAccount(AccountInputDto wallBracketInputDto) {
        Account newAccount = transferInputDtoToAccount(wallBracketInputDto);
        return accountRepository.save(newAccount);
    }

    // Functie voor PutMapping.
    public AccountOutputDto updateAccount(Long id, AccountInputDto accountInputDto) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isPresent()) {

            Account accountUpdate = optionalAccount.get();

            if (accountInputDto.getUsername() != null) {
                accountUpdate.setUsername(accountInputDto.getUsername());
            }
            if (accountInputDto.getFirstName() != null) {
                accountUpdate.setFirstName(accountInputDto.getFirstName());
            }
            if (accountInputDto.getLastName() != null) {
                accountUpdate.setLastName(accountInputDto.getLastName());
            }
            if (accountInputDto.getAdress() != null) {
                accountUpdate.setAdress(accountInputDto.getAdress());
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
            accountUpdate.setEnabled(accountInputDto.isEnabled());

            Account updatedAccount = accountRepository.save(accountUpdate);
            return transferAccountToOutputDto(updatedAccount);
        } else {
            throw new RecordNotFoundException("Account not found!");
        }
    }


}



