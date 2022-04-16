package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Guide;
import com.example.model.dto.GuideDto;
import com.example.service.GuideService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/guides")
public class GuideController {

    private GuideService guideService;

    @Autowired
    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @GetMapping
    public List<GuideDto> findAllRoles() {
        return guideService.findAll().stream().map(Guide::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    public GuideDto newGuide(@RequestBody Guide newGuide) {
        return guideService.save(newGuide).convertToDto();
    }

    @GetMapping("/{id}")
    public GuideDto getGuide(@PathVariable("id") Long id) throws NotFoundException {
        return guideService.getById(id).map(Guide::convertToDto).orElseThrow(() -> new NotFoundException("This guide not exist"));
    }

    @PutMapping("/{id}")
    public GuideDto editGuide(@PathVariable("id") Long id, @RequestBody Guide newGuide) throws NoEntityException {
        return guideService.getById(id)
                .map(guide -> {
                    guide.setLogin(newGuide.getLogin());
                    guide.setPassword(newGuide.getPassword());
                    guide.setFirstName(newGuide.getFirstName());
                    guide.setLastName(newGuide.getLastName());
                    guide.setPosition(newGuide.getPosition());
                    guide.setRole(newGuide.getRole());
                    return guideService.save(guide).convertToDto();
                })
                .orElseThrow(() -> new NoEntityException("This guide not exist"));
    }

    @DeleteMapping("/{id}")
    public void deleteGuide(@PathVariable("id") Long id) {
        guideService.deleteById(id);
    }
}
