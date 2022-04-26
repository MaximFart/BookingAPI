package com.example.controller;

import com.example.dto.AuthorizationRequestDto;
import com.example.model.User;
import com.example.model.exception.EntityNotFoundException;
import com.example.security.jwt.JwtTokenProvider;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public AuthorizationController(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public ResponseEntity<String> authorization(@RequestBody AuthorizationRequestDto dto) {
        User user = userService.findByUsername(dto.getUsername()).orElseThrow(() -> new EntityNotFoundException("User is not found", HttpStatus.NO_CONTENT));
        if (user.getPassword().equals(dto.getPassword()))
            return new ResponseEntity<>(tokenProvider.createToken(dto.getUsername()), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
