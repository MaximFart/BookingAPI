package com.example.controller;

import com.example.dto.RegistrationRequestDto;
import com.example.dto.UserDto;
import com.example.model.User;
import com.example.model.exception.EntityNotFoundException;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reg")
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<UserDto> registration(@RequestBody RegistrationRequestDto dto) {
        User user = new User(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getUsername(),
                dto.getPassword(),
                roleService.findByName("ROLE_USER").orElseThrow(() -> new EntityNotFoundException("Role is not found", HttpStatus.NO_CONTENT))
        );
        return new ResponseEntity<>(userService.save(user).convertToDto(), HttpStatus.OK);
    }
}
 