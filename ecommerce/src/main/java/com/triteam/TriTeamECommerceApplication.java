package com.triteam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.triteam.model.User;
import com.triteam.repository.UserRepository;

@SpringBootApplication
public class TriTeamECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TriTeamECommerceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if an admin user already exists so we don't recreate it every restart
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                // Encrypt the password 'admin123'
                admin.setPassword(passwordEncoder.encode("admin123"));
                // Assign the admin role (matches hasRole("ADMIN") in your security config)
                admin.setRole("ROLE_ADMIN");

                userRepository.save(admin);
                System.out.println(">> Default admin user created: username [admin] / password [admin123]");
            }
        };
    }
}
