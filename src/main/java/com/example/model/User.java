package com.example.model;

import com.example.model.dto.UserDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Person {

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

    public User() {
    }

    public UserDto convertToDto() {
        UserDto userDto = new UserDto();
        userDto.setId(getId());
        userDto.setLogin(getLogin());
        userDto.setPassword(getPassword());
        userDto.setFirstName(getFirstName());
        userDto.setLastName(getLastName());
        userDto.setRole(role.getName());
        return userDto;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
