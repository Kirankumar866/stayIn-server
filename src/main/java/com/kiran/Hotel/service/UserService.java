package com.kiran.Hotel.service;
import org.springframework.validation.Validator;
import com.kiran.Hotel.exception.UserAlreadyExistsException;
import com.kiran.Hotel.model.User;
import com.kiran.Hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepo;
    private final PasswordEnCoder pass;

    @Override
    public User registerUser(User user) {
        if(userRepo.existByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return List.of();
    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public User getUser(String email) {
        return null;
    }
}

