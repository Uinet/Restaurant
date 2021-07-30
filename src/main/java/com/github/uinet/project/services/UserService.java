package com.github.uinet.project.services;

import com.github.uinet.project.entity.Role;
import com.github.uinet.project.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        ArrayList<Role> role = new ArrayList<>();
        role.add(Role.USER);
        return User.builder()
                .username(s)
                .password(encoder.encode("password"))
                .authorities(role)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
}
