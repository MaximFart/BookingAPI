package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.User;
import com.example.model.dto.UserDto;
import com.example.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> findAllUsers() {
        return userService.findAll().stream().map(User::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    public UserDto newUser(@RequestBody User newUser) {
        return userService.save(newUser).convertToDto();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) throws NotFoundException {
        return userService.getById(id).map(User::convertToDto).orElseThrow(() -> new NotFoundException("This tour not exist"));
    }

    @PutMapping("/{id}")
    public UserDto editUser(@PathVariable("id") Long id, @RequestBody User newUser) throws NoEntityException {
        return userService.getById(id)
                .map(user -> {
                    user.setLogin(newUser.getLogin());
                    user.setPassword(newUser.getPassword());
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setRole(newUser.getRole());
                    return userService.save(user).convertToDto();
                })
                .orElseThrow(() -> new NoEntityException("This user not exist"));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }
}
