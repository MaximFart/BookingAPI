package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Tour;
import com.example.model.dto.TourDto;
import com.example.service.TourService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tours")
public class TourController {

    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public List<TourDto> findAllTours() {
        return tourService.findAll().stream().map(Tour::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TourDto getTour(@PathVariable("id") Long id) throws NoEntityException {
        return tourService.getById(id).map(Tour::convertToDto).orElseThrow(NoEntityException::new);
    }

    @PostMapping
    public TourDto newTour(@RequestBody Tour tour) {
        return tourService.save(tour).convertToDto();
    }

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

    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable("id") Long id) {
        tourService.deleteById(id);
    }
}
