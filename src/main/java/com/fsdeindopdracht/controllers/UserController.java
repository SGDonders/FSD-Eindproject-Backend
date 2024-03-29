package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.dtos.registerDto.RegisterDto;
import com.fsdeindopdracht.execeptions.BadRequestException;
import com.fsdeindopdracht.execeptions.UsernameNotFoundException;
import com.fsdeindopdracht.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // GetMapping request for an user.
    @GetMapping(value = "/{username}")
    public ResponseEntity<RegisterDto> getUser(@PathVariable("username") String username) {
        try {
            RegisterDto user = userService.getUser(username);
            return ResponseEntity.ok().body(user);
        } catch (UsernameNotFoundException ex) {

            return ResponseEntity.notFound().build();
        }
    }


    // GetMapping request for all users.
    @GetMapping(value = "")
    public ResponseEntity<List<RegisterDto>> getUsers() {

        List<RegisterDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }


    // PutMapping request for an user.
    @PutMapping(value = "/{username}")
    public ResponseEntity<RegisterDto> updateKlant(@PathVariable("username") String username, @RequestBody RegisterDto dto) {

        userService.updateUser(username, dto);

        return ResponseEntity.noContent().build();
    }


    // DeleteMapping for an user-account.
    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteKlant(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User successful deleted");
    }

    //----------------------------------------------User authorities----------------------------------------------------


    // GetMapping request for user authority.
    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }


    // PostMapping request for user authority.
    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }


    // DeleteMapping for user authority.
    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}