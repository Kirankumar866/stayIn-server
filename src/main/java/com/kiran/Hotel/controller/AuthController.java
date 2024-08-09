package com.kiran.Hotel.controller;

import com.kiran.Hotel.Security.jwt.JwtUtils;
import com.kiran.Hotel.Security.user.HotelUserDetails;
import com.kiran.Hotel.exception.UserAlreadyExistsException;
import com.kiran.Hotel.model.User;
import com.kiran.Hotel.request.LoginRequest;
import com.kiran.Hotel.response.JwtResponse;
import com.kiran.Hotel.service.IUserService;
import com.kiran.Hotel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    private final AuthenticationManager authManager;

    private final JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user){


        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successfull!");

        }catch(UserAlreadyExistsException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication =
                authManager
                        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        HotelUserDetails userDetails = (HotelUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(new JwtResponse(
                userDetails.getId(),
                userDetails.getEmail(),
                jwt,
                roles
        ));

    }

}
