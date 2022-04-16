package com.example.model;

import com.example.model.dto.GuideDto;
import com.example.model.dto.UserDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guides")
public class Guide extends Person {

    private String position;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "guide")
    private List<Booking> bookings = new ArrayList<>();

    public Guide() {
    }

    public GuideDto convertToDto() {
        GuideDto guideDto = new GuideDto();
        guideDto.setId(getId());
        guideDto.setLogin(getLogin());
        guideDto.setPassword(getPassword());
        guideDto.setFirstName(getFirstName());
        guideDto.setLastName(getLastName());
        guideDto.setPosition(position);
        guideDto.setRole(role.getName());

        return guideDto;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
