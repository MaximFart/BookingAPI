package com.example.controller;

import com.example.dto.AuthorizationRequestDto;
import com.example.model.User;
import com.example.model.exception.EntityNotFoundException;
import com.example.security.jwt.JwtTokenProvider;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public AuthorizationController(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping
    public String getAuthorization(@ModelAttribute("dto") AuthorizationRequestDto dto) {
        return "login";
    }

    @GetMapping("/http-servlet-response")
    public String usingHttpServletResponse(@RequestParam String token, HttpServletResponse response) {
        response.addHeader("Authorization", token);
        return "complete";
    }

    @PostMapping
    public String authorization(@ModelAttribute("dto") AuthorizationRequestDto dto, Model model) {
        User user = userService.findByUsername(dto.getUsername()).orElseThrow(() -> new EntityNotFoundException("User is not found", HttpStatus.NO_CONTENT));
        if (user.getPassword().equals(dto.getPassword())) {
            model.addAttribute("token", tokenProvider.createToken(dto.getUsername()));
            return "token";
        } else {
            return null;
        }
    }
}
