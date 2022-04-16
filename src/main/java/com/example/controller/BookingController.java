package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Booking;
import com.example.model.Guide;
import com.example.model.dto.BookingDto;
import com.example.service.BookingService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingDto> findAllBookings() {
        return bookingService.findAll().stream().map(Booking::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    public BookingDto newBooking(@RequestBody Booking newBooking) {
        return bookingService.save(newBooking).convertToDto();
    }

    @GetMapping("/{id}")
    public BookingDto getBooking(@PathVariable("id") Long id) throws NotFoundException {
        return bookingService.getById(id).map(Booking::convertToDto).orElseThrow(() -> new NotFoundException("This booking not exist"));
    }

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

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable("id") Long id) {
        bookingService.deleteById(id);
    }
}
