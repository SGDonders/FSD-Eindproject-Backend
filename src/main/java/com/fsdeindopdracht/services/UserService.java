package com.fsdeindopdracht.services;

import com.fsdeindopdracht.dtos.registerDto.RegisterDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Account;
import com.fsdeindopdracht.models.User;
import com.fsdeindopdracht.models.Authority;
import com.fsdeindopdracht.repositories.AccountRepository;
import com.fsdeindopdracht.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository) {
        this.userRepository = userRepository;

        this.accountRepository = accountRepository;
    }


    // Function for getMapping a user.
    public RegisterDto getUser(String username) {
        RegisterDto dto = new RegisterDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = fromUser(user.get());
        }else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }


    // Function for getMapping all users.
    public List<RegisterDto> getUsers() {
        List<RegisterDto> collection = new ArrayList<>();
        List <User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }


    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

//    public String createUser(RegisterDto userDto) {
////        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
//        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
////        userDto.setApikey(randomString);
//        User newUser = userRepository.save(toUser(userDto));
//        return newUser.getUsername();
//    }


    // Function for deleteMapping user-account.
    public void deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findById(username);
        Optional<Account> optionalAccount = accountRepository.findById(username);
        if(optionalUser.isEmpty() && optionalAccount.isEmpty()) {
            throw new UsernameNotFoundException("User already removed or doesn't exist!");
        }else {
            userRepository.deleteById(username);
            accountRepository.deleteById(username);
        }
    }


    // Function for putMapping user password.
    public void updateUser(String username, RegisterDto newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(user);
    }


    //--------------------------------------------User authorities------------------------------------------------------


    // Function for getMapping authorities.
    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        RegisterDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }


    // Function for adding new authority.
    public void addAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }


    // Function for remove authority.
    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }


    // Mapper method user to registerDTo.
    public static RegisterDto fromUser(User user){

        var dto = new RegisterDto();

        dto.userName = user.getUsername();
        dto.password = user.getPassword();
        dto.setAuthorities(user.getAuthorities());
        if(dto.getAccount()== null) {
            dto.setAccount(user.getAccount());
        }
        return dto;
    }


    // Mapper method registerDto to user.
    public User toUser(RegisterDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        if(userDto.getAccount()!=null) {
            user.setAccount(userDto.getAccount());
        }
        return user;
    }

}
