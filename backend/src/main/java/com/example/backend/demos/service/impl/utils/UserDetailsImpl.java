package com.example.backend.demos.service.impl.utils;

import com.example.backend.demos.pojo.User;
import com.example.backend.demos.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsImpl {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public String getHash1() {
        return user.getHash1();
    }

    public String getUsername() {
        return user.getUsername();
    }


    // 添加getter和setter方法，以便能访问和修改user对象
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}