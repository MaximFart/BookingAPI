package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Booking;
import com.example.model.User;
import com.example.service.BookingService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.security.SecurityConstants.*;

@Controller
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private BookingService bookingService;
    private UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping
    public String findAllRoles(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        return "booking/bookings";
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/{id}")
    public String getRole(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("booking", bookingService.getById(id).orElseThrow(NoEntityException::new));
        return "booking/show";
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping("/new")
    public String create(@ModelAttribute("booking") Booking booking) {
        return "booking/new";
    }

    @PreAuthorize(AUTH_ADMIN)
    @PostMapping
    public String newBooking(@ModelAttribute("booking") Booking booking) {
        bookingService.save(booking);
        return "redirect:/api/v1/bookings";
    }

    @PreAuthorize(AUTH_USER)
    @PostMapping("/{username}/{id}/add_user")
    public String addUsers(@PathVariable(value = "username") String username, @PathVariable(value = "id") Long id) throws NoEntityException {
        bookingService.addUser(userService.findByUsername(username).orElseThrow(NoEntityException::new), id);
        return "redirect:/api/v1/bookings";
    }

    @PreAuthorize(AUTH_USER)
    @PostMapping("/{username}/{id}/remove_user")
    public String removeUsers(@PathVariable(value = "username") String username, @PathVariable(value = "id") Long id) throws NoEntityException {
        bookingService.removeUser(userService.findByUsername(username).orElseThrow(NoEntityException::new), id);
        return "redirect:/api/v1/bookings";
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping("/{id}/edit")
    public String editRole(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("booking", bookingService.getById(id).orElseThrow(NoEntityException::new));
        return "booking/edit";
    }


    @PreAuthorize(AUTH_ADMIN)
    @PutMapping("/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("booking") Booking booking) {
        bookingService.save(booking);
        return "redirect:/api/v1/bookings";
    }

    @PreAuthorize(AUTH_ADMIN)
    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable("id") Long id) {
        bookingService.deleteById(id);
        return "redirect:/api/v1/bookings";
    }
}
