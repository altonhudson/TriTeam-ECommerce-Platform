package com.triteam.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.triteam.model.User;
import com.triteam.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found in the database."));
    }

    public List<User> findAllUsers() {
        return repo.findAll();
    }

    public User findUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found with ID: " + id));
    }

    public void saveUser(User user) {
        if (user.getId() == null || !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        repo.save(user);
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
