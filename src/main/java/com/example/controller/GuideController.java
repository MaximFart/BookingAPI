package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Guide;
import com.example.dto.GuideDto;
import com.example.service.GuideService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/guides")
public class GuideController {

    private GuideService guideService;

    @Autowired
    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping
    public List<GuideDto> findAllRoles() {
        return guideService.findAll().stream().map(Guide::convertToDto).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public GuideDto getGuide(@PathVariable("id") Long id) throws NotFoundException {
        return guideService.getById(id).map(Guide::convertToDto).orElseThrow(() -> new NotFoundException("This guide not exist"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public GuideDto newGuide(@RequestBody Guide newGuide) {
        return guideService.save(newGuide).convertToDto();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public GuideDto editGuide(@PathVariable("id") Long id, @RequestBody Guide newGuide) throws NoEntityException {
        return guideService.getById(id)
                .map(guide -> {
                    guide.setFirstName(newGuide.getFirstName());
                    guide.setLastName(newGuide.getLastName());
                    return guideService.save(guide).convertToDto();
                })
                .orElseThrow(() -> new NoEntityException("This guide not exist"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteGuide(@PathVariable("id") Long id) {
        guideService.deleteById(id);
    }
}
