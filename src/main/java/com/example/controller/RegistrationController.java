package com.example.controller;

import com.example.dto.RegistrationRequestDto;
import com.example.dto.UserDto;
import com.example.model.User;
import com.example.model.exception.EntityNotFoundException;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getRegistration(@ModelAttribute("dto") RegistrationRequestDto dto) {
        return "registration";
    }

    @PostMapping
    public ResponseEntity<UserDto> registration(@ModelAttribute("dto") RegistrationRequestDto dto) {
        User user = new User(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getUsername(),
                dto.getPassword(),
                roleService.findByName(USER).orElseThrow(() -> new EntityNotFoundException("Role is not found", HttpStatus.NO_CONTENT))
        );
        return new ResponseEntity<>(userService.save(user).convertToDto(), HttpStatus.OK);
    }
}
 