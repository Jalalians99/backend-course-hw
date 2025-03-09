package com.example.bookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.bookstore.domain.User;
import com.example.bookstore.domain.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Test
    public void createNewUser() {
        // Create and save a user
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("password"));
        user.setEmail("test@example.com");
        user.setRole("USER");
        
        userRepository.save(user);
        
        // Verify user was saved with an ID
        assertThat(user.getId()).isNotNull();
    }
    
    @Test
    public void findByUsername() {
        // Create and save users
        User user1 = new User();
        user1.setUsername("alice");
        user1.setPassword(passwordEncoder.encode("alicepass"));
        user1.setEmail("alice@example.com");
        user1.setRole("USER");
        
        User user2 = new User();
        user2.setUsername("bob");
        user2.setPassword(passwordEncoder.encode("bobpass"));
        user2.setEmail("bob@example.com");
        user2.setRole("ADMIN");
        
        userRepository.save(user1);
        userRepository.save(user2);
        
        // Find user by username
        User found = userRepository.findByUsername("alice");
        
        // Verify correct user was found
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("alice");
        assertThat(found.getEmail()).isEqualTo("alice@example.com");
    }
    
    @Test
    public void deleteUser() {
        // Create and save a user
        User user = new User();
        user.setUsername("deleteuser");
        user.setPassword(passwordEncoder.encode("deletepass"));
        user.setEmail("delete@example.com");
        user.setRole("USER");
        
        userRepository.save(user);
        
        // Verify user exists
        Long userId = user.getId();
        assertThat(userRepository.findById(userId)).isPresent();
        
        // Delete the user
        userRepository.deleteById(userId);
        
        // Verify user no longer exists
        assertThat(userRepository.findById(userId)).isEmpty();
    }
} 