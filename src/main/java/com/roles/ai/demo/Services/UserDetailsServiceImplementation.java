package com.roles.ai.demo.Services;


import com.roles.ai.demo.Entities.Role;
import com.roles.ai.demo.Entities.User;
import com.roles.ai.demo.Repository.RoleRepository;
import com.roles.ai.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            if(user.getRoles()!=null)
            for(Role r : user.getRoles()) {
                String ROLE_PREFIX = "ROLE_";
                grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + r.getName()));
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }
        catch (UsernameNotFoundException e){
            System.out.println("Bład autentykacji: "+ e.getMessage());
        }
        return null;
    }

}
