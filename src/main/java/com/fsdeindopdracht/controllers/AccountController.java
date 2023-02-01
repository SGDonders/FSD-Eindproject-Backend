package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.dtos.inputDto.AccountInputDto;
import com.fsdeindopdracht.dtos.outputDto.AccountOutputDto;
import com.fsdeindopdracht.dtos.registerDto.RegisterDto;
import com.fsdeindopdracht.services.AccountService;
import com.fsdeindopdracht.services.UserService;
import com.fsdeindopdracht.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins="*")

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }


    // GetMapping request alle accounts.
    @GetMapping("")
    public ResponseEntity<List<AccountOutputDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // GetMapping request voor één account.
    @GetMapping("{id}")
    public ResponseEntity<AccountOutputDto> getAccount(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    // DeleteMapping request voor een account.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id) {
        accountService.deleteAccount(id); //
        return ResponseEntity.noContent().build();
    }

    // PostMapping request voor een account.
    @PostMapping("")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) throws ValidationException {

        Utils.reportErrors(bindingResult);

        AccountOutputDto savedAccount = accountService.createUserAccount(registerDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath
                ().path("/accounts/" + savedAccount).toUriString());

        userService.addAuthority(registerDto.getUsername(),"ROLE_USER");
        return ResponseEntity.created(uri).body(savedAccount);
    }

    // PutMapping request voor een account.
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAccount(@PathVariable String id, @RequestBody AccountInputDto accountInputDto) {
        AccountOutputDto accountOutputDto = accountService.updateAccount(id, accountInputDto);
        return ResponseEntity.ok().body(accountOutputDto);
    }

}