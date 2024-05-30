package com.example.backend.demos.service;

import com.example.backend.demos.pojo.User;

public interface UserService {
    void registerUser(String username, String hash1);
    boolean loginUser(String username, String hash2, String verificationCode);
    boolean changePassword(String username, String hash1, String newHash1);
    String getHash1ByUsername(String username);
}

