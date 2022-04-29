package com.example.service;

import com.example.model.Booking;
import com.example.model.User;
import com.example.model.exception.EntityNotFoundException;
import com.example.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Transactional
    public Optional<Booking> getById(Long id) {
        return bookingRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Transactional
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Transactional
    public Booking addUser(User user, Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        booking.getUsers().add(user);
        return bookingRepository.save(booking);

    }
}
