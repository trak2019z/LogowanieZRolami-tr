package com.roles.ai.demo.Services;


import com.roles.ai.demo.Entities.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
