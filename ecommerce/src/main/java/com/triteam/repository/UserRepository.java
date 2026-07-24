package com.triteam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triteam.model.User;

public interface UserRepository extends JpaRepository<Long, User> {

    Optional<User> findByUsername(String username);

}
