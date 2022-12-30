package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.dtos.inputDto.AccountInputDto;
import com.fsdeindopdracht.dtos.outputDto.AccountOutputDto;
import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.services.AccountService;
import com.fsdeindopdracht.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/accountcontrollers")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    // GetMapping request alle accounts.
    @GetMapping("")
    public ResponseEntity<List<AccountOutputDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // GetMapping request voor één account.
    @GetMapping("{id}")
    public ResponseEntity<AccountOutputDto> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    // DeleteMapping request voor een account.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id); //
        return ResponseEntity.noContent().build();
    }

    // PostMapping request voor een account.
    @PostMapping("")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountInputDto accountInputDto, BindingResult bindingResult) throws ValidationException {

        Utils.reportErrors(bindingResult);

        Account savedAccount = accountService.createAccount(accountInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath
                ().path("/account/" + savedAccount.getId()).toUriString());

        return ResponseEntity.created(uri).body(savedAccount);
    }

    // PutMapping request voor een account.
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAccount(@PathVariable Long id, @RequestBody AccountInputDto accountInputDto) {
        AccountOutputDto accountOutputDto = accountService.updateAccount(id, accountInputDto);
        return ResponseEntity.ok().body(accountOutputDto);
    }

}

