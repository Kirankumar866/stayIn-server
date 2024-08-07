package com.kiran.Hotel.service;
import com.kiran.Hotel.exception.UserAlreadyExistsException;
import com.kiran.Hotel.model.Role;
import com.kiran.Hotel.model.User;
import com.kiran.Hotel.repository.RoleRepository;
import com.kiran.Hotel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;


    @Override
    public User registerUser(User user) {
        System.out.println("user" + user);

        if (user.getEmail() == null) {
            throw new IllegalArgumentException("Email cannot be null" + user.getEmail());
        }
        if(userRepo.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null" + user.getPassword());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepo.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));
        return userRepo.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {

        User theUser = getUser(email);
        if(theUser != null){
            userRepo.deleteByEmail(email);
        }
    }

    @Override
    public User getUser(String email) {
        return userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found!"));
    }
}

