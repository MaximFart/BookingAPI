package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Role;
import com.example.dto.RoleDto;
import com.example.service.RoleService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.security.SecurityConstants.AUTH_ADMIN;
import static com.example.security.SecurityConstants.AUTH_ALL;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping
    public List<RoleDto> findAllRoles() {
        return roleService.findAll().stream().map(Role::convertToDto).collect(Collectors.toList());
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/{id}")
    public RoleDto getRole(@PathVariable("id") Long id) throws NotFoundException {
        return roleService.getById(id).map(Role::convertToDto).orElseThrow(() -> new NotFoundException("This role not exist"));
    }

    @PreAuthorize(AUTH_ADMIN)
    @PostMapping
    public RoleDto newRole(@RequestBody Role newRole) {
        return roleService.save(newRole).convertToDto();
    }

    @PreAuthorize(AUTH_ADMIN)
    @PutMapping("/{id}")
    public RoleDto editRole(@PathVariable("id") Long id, @RequestBody Role newRole) throws NoEntityException {
        return roleService.getById(id)
                .map(role -> {
                    role.setName(newRole.getName());
                    return roleService.save(role).convertToDto();
                })
                .orElseThrow(() -> new NoEntityException("This role not exist"));
    }

    @PreAuthorize(AUTH_ADMIN)
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") Long id) {
        roleService.deleteById(id);
    }
}
