package com.example.backend.demos.controller.user;

import com.example.backend.demos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String hash1 = request.get("hash1");
        System.out.println("Received data: username=" + username + ", hash1=" + hash1);
        try {
            userService.registerUser(username, hash1);
            return ResponseEntity.ok().body("注册成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("注册失败：" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String hash2 = request.get("hash2");
        String verificationCode = request.get("verificationCode");

        System.out.println("Received data: username=" + username + ", hash2=" + hash2 + ", verificationCode=" + verificationCode);

        try {
            boolean success = userService.loginUser(username, hash2, verificationCode);
//              boolean success = true;

            if (success) {
                return ResponseEntity.ok().body("登录成功");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败：用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("登录失败：" + e.getMessage());
        }
    }
}
