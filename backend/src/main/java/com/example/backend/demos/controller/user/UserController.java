package com.example.backend.demos.controller.user;

import com.example.backend.demos.pojo.User;
import com.example.backend.demos.service.UserService;
import com.example.backend.demos.service.impl.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

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
                String hash1 = userService.getHash1ByUsername(username);
                String encryptedCode = AESUtil.encrypt(verificationCode, hash1);

                Map<String, String> response = new HashMap<>();
                response.put("message", "登录成功");
                response.put("encryptedCode", encryptedCode);

                return ResponseEntity.ok().body(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败：用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("登录失败：" + e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String hash1 = request.get("hash1");
        String newHash1 = request.get("newHash1");
        try {
            boolean success = userService.changePassword(username, hash1, newHash1);
            if (success) {
//                System.out.println("1.用户：username=" + username + ", 修改hash1=" + newHash1);
                return ResponseEntity.ok().body("密码修改成功");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密码修改失败：原密码错误");
            }
        } catch (Exception e) {
//            System.out.println("2.用户：username=" + username + ", 修改hash1=" + newHash1);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码修改失败：" + e.getMessage());
        }
    }
}
