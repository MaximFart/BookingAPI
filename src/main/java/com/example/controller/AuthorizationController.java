package com.example.controller;

import com.example.dto.AuthorizationRequestDto;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAuthorization(@ModelAttribute("dto") AuthorizationRequestDto dto) {
        return "authorization/login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "authorization/success";
    }
}
