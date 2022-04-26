package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Booking;
import com.example.dto.BookingDto;
import com.example.service.BookingService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping
    public List<BookingDto> findAllBookings() {
        return bookingService.findAll().stream().map(Booking::convertToDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public BookingDto getBooking(@PathVariable("id") Long id) throws NotFoundException {
        return bookingService.getById(id).map(Booking::convertToDto).orElseThrow(() -> new NotFoundException("This booking not exist"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public BookingDto newBooking(@RequestBody Booking newBooking) {
        return bookingService.save(newBooking).convertToDto();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public BookingDto editBooking(@PathVariable("id") Long id, @RequestBody Booking newBooking) throws NoEntityException {
        return bookingService.getById(id)
                .map(booking -> {
                    booking.setGuide(newBooking.getGuide());
                    booking.setUser(newBooking.getUser());
                    booking.setTour(newBooking.getTour());
                    return bookingService.save(booking).convertToDto();
                })
                .orElseThrow(() -> new NoEntityException("This booking not exist"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable("id") Long id) {
        bookingService.deleteById(id);
    }
}
