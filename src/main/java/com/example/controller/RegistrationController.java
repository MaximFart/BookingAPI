package com.example.controller;

import com.example.dto.RegistrationRequestDto;
import com.example.dto.UserDto;
import com.example.model.User;
import com.example.model.exception.EntityNotFoundException;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.security.SecurityConstants.USER;

@Controller
@RequestMapping("/api/v1/regist")
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getRegistration(@ModelAttribute("dto") RegistrationRequestDto dto) {
        return "authorization/registration";
    }

    @PostMapping
    public String registration(@ModelAttribute("dto") RegistrationRequestDto dto) throws Exception {
        User user = new User(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                roleService.findByName(USER).orElseThrow(() -> new EntityNotFoundException("Role is not found", HttpStatus.NO_CONTENT))
        );
        if (!userService.findByUsername(user.getUsername()).isPresent()) {
            userService.save(user);
        } else {
            throw new Exception("User just exist");
        }
        return "redirect:/api/v1/auth";
    }
}
 