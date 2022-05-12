package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Role;
import com.example.dto.RoleDto;
import com.example.model.Tour;
import com.example.service.RoleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.security.SecurityConstants.AUTH_ADMIN;
import static com.example.security.SecurityConstants.AUTH_ALL;

@Controller
@RequestMapping("/api/v1/roles")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping
    public String findAllRoles(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "role/roles";
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/{id}")
    public String getRole(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("role", roleService.getById(id).orElseThrow(NoEntityException::new));
        return "role/show";
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping("/new")
    public String create(@ModelAttribute("role") Role role) {
        return "role/new";
    }

    @PreAuthorize(AUTH_ADMIN)
    @PostMapping
    public String newRole(@ModelAttribute("role") Role role) {
        roleService.save(role);
        return "redirect:/api/v1/roles";
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping("/{id}/edit")
    public String editRole(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("role", roleService.getById(id).orElseThrow(NoEntityException::new));
        return "role/edit";
    }


    @PreAuthorize(AUTH_ADMIN)
    @PutMapping("/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("role") Role role) {
        roleService.save(role);
        return "redirect:/api/v1/roles";
    }

    @PreAuthorize(AUTH_ADMIN)
    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        return "redirect:/api/v1/roles";
    }
}
