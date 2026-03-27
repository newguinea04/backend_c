package com.backend.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.demo.model.User;
import com.backend.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        // Logika tambahan bisa ditaruh di sini (misal: enkripsi password)
        return userRepository.save(user);
    }

    @Override
    public List<User> getListuser() {
        return userRepository.findAll();
    }
}