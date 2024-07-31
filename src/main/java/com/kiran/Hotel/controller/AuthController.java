package com.kiran.Hotel.controller;

import com.kiran.Hotel.exception.UserAlreadyExistsException;
import com.kiran.Hotel.model.User;
import com.kiran.Hotel.service.IUserService;
import com.kiran.Hotel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final IUserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(User user){

        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successfull!");

        }catch(UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }


    }

}
