package com.example.security;

import com.example.model.User;
import com.example.security.jwt.SecurityUserFactory;
import com.example.service.GuideService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private UserService userService;
    private GuideService guideService;

    @Autowired
    public SecurityUserDetailsService(UserService userService, GuideService guideService) {
        this.userService = userService;
        this.guideService = guideService;
    }

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return SecurityUserFactory.create(user.get());
        } else {
            return SecurityUserFactory.create(guideService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username is " + username + " not found")));
        }
    }
}
