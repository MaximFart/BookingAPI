package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Tour;
import com.example.dto.TourDto;
import com.example.service.TourService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tours")
public class TourController {

    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping
    public List<TourDto> findAllTours() {
        return tourService.findAll().stream().map(Tour::convertToDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public TourDto getTour(@PathVariable("id") Long id) throws NoEntityException {
        return tourService.getById(id).map(Tour::convertToDto).orElseThrow(NoEntityException::new);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public TourDto newTour(@RequestBody Tour tour) {
        return tourService.save(tour).convertToDto();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public TourDto editTour(@PathVariable("id") Long id, @RequestBody Tour newTour) throws NotFoundException {
        return tourService.getById(id)
                .map(tour -> {
                    tour.setPrice(newTour.getPrice());
                    tour.setRoute(newTour.getRoute());
                    tour.setStart(newTour.getStart());
                    tour.setFinish(newTour.getFinish());
                    return tourService.save(tour).convertToDto();
                })
                .orElseThrow(() -> new NotFoundException("This tour not exist"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable("id") Long id) {
        tourService.deleteById(id);
    }
}
