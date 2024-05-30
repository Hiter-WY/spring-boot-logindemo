package com.example.backend.demos.service.impl.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.demos.mapper.UserMapper;
import com.example.backend.demos.pojo.User;
import com.example.backend.demos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void registerUser(String username, String hash1) {
        try {
            System.out.println("开始注册用户：username=" + username + ", hash1=" + hash1);
            User user = new User();
            user.setUsername(username);
            user.setHash1(hash1);
            userMapper.insert(user);
            System.out.println("用户注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("用户注册失败", e);
        }
    }

    @Override
    public boolean loginUser(String username, String hash2, String verificationCode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        try{
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
            String hash1 = user.getHash1();
            String calculatedHash2 = generateHash(hash1 + verificationCode);

            if (!calculatedHash2.equals(hash2)) {
                return false;
            }

            // 登录成功
            return true;
//        String generatedHash2 = generateHash(user.getHash1() + verificationCode);
//        return generatedHash2.equals(hash2);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("登录失败：" + e.getMessage());
        }
    }

    @Override
    public boolean changePassword(String username, String hash1, String newHash1) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        String localhash1 = user.getHash1();
//
//        System.out.println("Local hash1: " + localhash1);
//        System.out.println("Received hash1: " + hash1);
//        System.out.println("New hash1: " + newHash1);
        if (!localhash1.equals(hash1)) {
            System.out.println("Original password does not match.");
            return false;
        }
//        System.out.println("Hashes match. Proceeding to update password.");

        user.setHash1(newHash1);
        int rows = userMapper.updateById(user);
        if (rows > 0) {
            System.out.println("Password updated successfully.");
            return true;
        } else {
            System.out.println("Error updating password.");
            return false;
        }
    }

    @Override
    public String getHash1ByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user.getHash1();
    }

    private String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
