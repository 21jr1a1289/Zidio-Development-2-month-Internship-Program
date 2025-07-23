package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.model.Users;
import com.jobportal.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Users user) {
        try {
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists!"));
            }

            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already exists!"));
            }

            // Default role assignment
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("USER");
            } else {
                String role = user.getRole().toUpperCase();
                if (!role.equals("USER") && !role.equals("ADMIN")) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid role selected."));
                }
                user.setRole(role);
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "User registered successfully!", "role", user.getRole()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Server error during registration"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        try {
            Optional<Users> existingUser = userRepository.findByUsername(user.getUsername());

            if (existingUser.isPresent()) {
                Users u = existingUser.get();
                System.out.println("DB Password: " + u.getPassword());
                System.out.println("Entered Password: " + user.getPassword());

                boolean match = passwordEncoder.matches(user.getPassword(), u.getPassword());
                System.out.println("Password Match: " + match);

                if (match) {
                    return ResponseEntity.ok(Map.of(
                            "message", "Login successful!",
                            "username", u.getUsername(),
                            "role", u.getRole()
                    ));
                }
            }

            return ResponseEntity.badRequest().body(Map.of("error", "Invalid username or password"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Server error: " + e.getMessage()));
        }
    }

    
    

}
