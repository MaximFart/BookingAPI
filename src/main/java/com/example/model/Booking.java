package com.example.model;

import com.example.dto.BookingDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ManyToMany
    @JoinTable(
            name = "booking_user",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    public Booking() {
    }

    public BookingDto convertToDto() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(id);
        bookingDto.setUserDto(users.size());
        bookingDto.setTourDto(tour.convertToDto());
        bookingDto.setGuideDto(guide.convertToDto());
        return bookingDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }
}
