package com.example.security.jwt;

import com.example.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;

public class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                new ArrayList<SimpleGrantedAuthority>(){{ add(new SimpleGrantedAuthority(user.getRole().getName())); }}
        );
    }
}
