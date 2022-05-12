package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.security.SecurityConstants.*;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping
    public String findAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/users";
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/{id}")
    public String getTour(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("user", userService.getById(id).orElseThrow(NoEntityException::new));
        return "user/show";
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/new")
    public String create(@ModelAttribute("user") User user) {
        return "user/new";
    }

    @PreAuthorize(AUTH_ALL)
    @PostMapping
    public String newUser(@ModelAttribute("user") User user) throws NoEntityException {
        user.setRole(roleService.findByName(USER).orElseThrow(NoEntityException::new));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/api/v1/users";
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/{id}/edit")
    public String editTour(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("user", userService.getById(id).orElseThrow(NoEntityException::new));
        return "user/edit";
    }


    @PreAuthorize(AUTH_ALL)
    @PutMapping("/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);
        return "redirect:/api/v1/users";
    }

    @PreAuthorize(AUTH_ADMIN)
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/api/v1/users";
    }
}
