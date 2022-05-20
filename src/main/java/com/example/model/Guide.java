package com.example.model;

import com.example.dto.GuideDto;
import com.example.security.jwt.SecurityGuide;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guides")
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = true)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "guide")
    private List<Booking> bookings = new ArrayList<>();

    public Guide() {
    }

    public GuideDto convertToDto() {
        GuideDto guideDto = new GuideDto();
        guideDto.setId(id);
        guideDto.setFirstName(firstName);
        guideDto.setLastName(lastName);

        return guideDto;
    }

    public SecurityGuide toSecurityGuide() {
        return new SecurityGuide(
                id,
                username,
                password,
                firstName,
                lastName,
                new ArrayList<SimpleGrantedAuthority>(){{ add(new SimpleGrantedAuthority(role.getName())); }}
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.lastName;
    }
}
