package com.kiran.Hotel.repository;

import com.kiran.Hotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existByEmail(String email);
}
