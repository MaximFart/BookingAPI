package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Booking;
import com.example.dto.BookingDto;
import com.example.model.User;
import com.example.security.SecurityConstants;
import com.example.service.BookingService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.security.SecurityConstants.AUTH_ADMIN;
import static com.example.security.SecurityConstants.AUTH_ALL;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping
    public List<BookingDto> findAllBookings() {
        return bookingService.findAll().stream().map(Booking::convertToDto).collect(Collectors.toList());
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/{id}")
    public BookingDto getBooking(@PathVariable("id") Long id) throws NotFoundException {
        return bookingService.getById(id).map(Booking::convertToDto).orElseThrow(() -> new NotFoundException("This booking not exist"));
    }

    @PreAuthorize(AUTH_ADMIN)
    @PostMapping
    public BookingDto newBooking(@RequestBody Booking newBooking) {
        return bookingService.save(newBooking).convertToDto();
    }

    @PreAuthorize(AUTH_ADMIN)
    @PostMapping("/{id}")
    public BookingDto addUser(@PathVariable("id") Long id, @RequestBody User user) {
        return bookingService.addUser(user, id).convertToDto();
    }

    @PreAuthorize(AUTH_ADMIN)
    @PutMapping("/{id}")
    public BookingDto editBooking(@PathVariable("id") Long id, @RequestBody Booking newBooking) throws NoEntityException {
        return bookingService.getById(id)
                .map(booking -> {
                    booking.setGuide(newBooking.getGuide());
                    booking.setUsers(newBooking.getUsers());
                    booking.setTour(newBooking.getTour());
                    return bookingService.save(booking).convertToDto();
                })
                .orElseThrow(() -> new NoEntityException("This booking not exist"));
    }

    @PreAuthorize(AUTH_ADMIN)
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable("id") Long id) {
        bookingService.deleteById(id);
    }
}
