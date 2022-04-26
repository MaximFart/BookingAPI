package com.example.model;

import com.example.dto.RoleDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public RoleDto convertToDto() {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(id);
        roleDto.setName(name);
        return roleDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
