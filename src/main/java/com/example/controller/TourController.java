package com.example.controller;

import com.example.model.Tour;
import com.example.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tours")
public class TourController {

    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping()
    public String findAll(Model model) {
        model.addAttribute("tours", tourService.findAll());
        return "tour/tours";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("tour") Tour tour) {
        return "/tour/new";
    }

    @PostMapping()
    public String saveTour(@ModelAttribute("tour") Tour tour) {
        tourService.save(tour);
        return "redirect:/tours";
    }
}
