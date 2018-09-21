package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userMapper.loadUserByUsername(username);

        String pw = new BCryptPasswordEncoder().encode("123456");
        String pw2 = new BCryptPasswordEncoder().encode("123456");
        System.out.println(pw);
        System.out.println(pw2);
        System.out.println(user.getPassword());

        UserBuilder builder;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            builder.roles(user.getRoles().split(","));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void hasRole() {

    }
}
