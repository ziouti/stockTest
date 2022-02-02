package com.stock;

import com.stock.model.Personne;
import com.stock.repository.Auth;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(EStockApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(Auth userRepository, PasswordEncoder bCryptPasswordEncoder) {
        return args -> {
            //This is an admin
            Personne admin = new Personne();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(bCryptPasswordEncoder.encode("12345"));
            admin.setRole(1);
            userRepository.save(admin);

            Personne user = new Personne();
            user.setUsername("user");
            user.setEmail("user@gmail.com");
            user.setPassword(bCryptPasswordEncoder.encode("12345"));
            user.setRole(0);
            userRepository.save(user);
        };
    }
}
