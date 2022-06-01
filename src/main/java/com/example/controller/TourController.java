package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Tour;
import com.example.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.security.SecurityConstants.AUTH_ADMIN;
import static com.example.security.SecurityConstants.AUTH_ALL;

@Controller
@RequestMapping("/api/v1/tours")
public class TourController {

    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping
    public String findAllTours(Model model) {
        model.addAttribute("tours", tourService.findAll());
        return "tour/tours";
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping("/{id}")
    public String getTour(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("tour", tourService.getById(id).orElseThrow(NoEntityException::new));
        return "tour/show";
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping("/new")
    public String create(@ModelAttribute("tour") Tour tour) {
        return "tour/new";
    }

    @PreAuthorize(AUTH_ADMIN)
    @PostMapping
    public String newTour(@ModelAttribute("tour") Tour tour) {
        tourService.save(tour);
        return "redirect:/api/v1/tours";
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping("/{id}/edit")
    public String editTour(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("tour", tourService.getById(id).orElseThrow(NoEntityException::new));
        return "tour/edit";
    }


    @PreAuthorize(AUTH_ADMIN)
    @PutMapping("/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("tour") Tour tour) {
        tourService.save(tour);
        return "redirect:/api/v1/tours";
    }

    @PreAuthorize(AUTH_ADMIN)
    @DeleteMapping("/{id}")
    public String deleteTour(@PathVariable("id") Long id) {
        tourService.deleteById(id);
        return "redirect:/api/v1/tours";
    }
}
