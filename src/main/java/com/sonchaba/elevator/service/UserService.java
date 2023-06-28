package com.sonchaba.elevator.service;

import com.sonchaba.elevator.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getCurrentUser() {
        // Example: Return the currently authenticated user from security context
        // Replace with your actual implementation
        return new User(1L, "John Doe");
    }
}
