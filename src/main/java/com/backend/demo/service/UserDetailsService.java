package com.backend.demo.service;

import com.backend.demo.model.User;
import com.backend.demo.model.UserDetail; // Pastikan ini mengarah ke model/UserDetail yang benar
import com.backend.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Mencari user berdasarkan email di database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan dengan email: " + email));

        // Mengembalikan implementasi UserDetails (UserDetail.java)
        return new UserDetail(user);
    }
}