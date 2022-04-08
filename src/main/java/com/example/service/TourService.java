package com.example.service;

import com.example.model.Tour;
import com.example.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    private TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Transactional
    public void save(Tour tour) {
        tourRepository.save(tour);
    }

    @Transactional
    public Optional<Tour> getById(Long id) {
        return tourRepository.findById(id);
    }

    @Transactional
    public void update(Tour tour) {
        tourRepository.save(tour);
    }

    @Transactional
    public void delete(Long id) {
        tourRepository.deleteById(id);
     }

    @Transactional
    public List<Tour> findAll() {
       return tourRepository.findAll();
    }
}
