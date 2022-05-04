package com.example.controller;

import com.example.dto.AuthorizationRequestDto;
import com.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    private final UserService userService;

    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAuthorization(@ModelAttribute("dto") AuthorizationRequestDto dto) {
        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "success";
    }

//    @PostMapping
//    public String authorization(@ModelAttribute("dto") AuthorizationRequestDto dto, Model model) {
//        User user = userService.findByUsername(dto.getUsername()).orElseThrow(() -> new EntityNotFoundException("User is not found", HttpStatus.NO_CONTENT));
//        if (user.getPassword().equals(dto.getPassword())) {
//            model.addAttribute("token", tokenProvider.createToken(dto.getUsername()));
//            return "token";
//        } else {
//            return null;
//        }
//    }
}
