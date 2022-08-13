package com.example.oauth2Jwt.api.controller.service;

import com.example.oauth2Jwt.api.entity.user.User;
import com.example.oauth2Jwt.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}