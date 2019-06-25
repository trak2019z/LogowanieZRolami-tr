package com.roles.ai.demo.Services;

import com.roles.ai.demo.Entities.Role;
import com.roles.ai.demo.Entities.User;
import com.roles.ai.demo.Repository.RoleRepository;
import com.roles.ai.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(user.getIsAdmin()==null){
            Set<Role> roles = new HashSet<>();
            Role role = new Role();
            role.setName("USER");
            roles.add(role);
            user.setRoles(roles);
        }
        else{
            Set<Role> roles = new HashSet<>();
            Role role = new Role();
            role.setName("ADMIN");
            roles.add(role);
            user.setRoles(roles);
            user.setIsAdmin(user.getIsAdmin());
        }
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
