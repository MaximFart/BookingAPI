package com.example.model;

import com.example.dto.BookingDto;

import javax.persistence.*;


@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    public Booking() {
    }

    public BookingDto convertToDto() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(id);
        bookingDto.setTourDto(tour.convertToDto());
        bookingDto.setUserDto(user.convertToDto());
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }
}
