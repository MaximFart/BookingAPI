package com.example.security.jwt;

import com.example.model.Guide;
import com.example.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;

public class SecurityUserFactory {

    public SecurityUserFactory() {
    }

    public static SecurityUser create(User user) {
        return new SecurityUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                new ArrayList<SimpleGrantedAuthority>(){{ add(new SimpleGrantedAuthority(user.getRole().getName())); }}
        );
    }
    public static SecurityUser create(Guide guide) {
        return new SecurityUser(
                guide.getId(),
                guide.getUsername(),
                guide.getPassword(),
                guide.getFirstName(),
                guide.getLastName(),
                new ArrayList<SimpleGrantedAuthority>(){{ add(new SimpleGrantedAuthority(guide.getRole().getName())); }}
        );
    }
}
