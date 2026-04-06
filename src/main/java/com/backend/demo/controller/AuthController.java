package com.backend.demo.controller;

import com.backend.demo.dto.LoginUserDto;
import com.backend.demo.dto.RegisterUserDto;
import com.backend.demo.dto.LoginResponse;
import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto request) {
        User user = new User();
        // Method ini muncul otomatis jika di RegisterUserDto ada @Data
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto request) {

        System.out.println("Mencoba login untuk email: " + request.getEmail());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), // Pastikan di LoginUserDto ada @Data
                request.getPassword()
            )
        );

        // 2. Jika sukses, buatkan token
        String token = jwtUtils.generateToken(request.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}