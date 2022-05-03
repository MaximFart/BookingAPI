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

import static com.example.security.SecurityConstants.AUTH_ADMIN;
import static com.example.security.SecurityConstants.AUTH_ALL;

@RestController
@RequestMapping("/api/v1/guides")
public class GuideController {

    private GuideService guideService;

    @Autowired
    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

//    @PreAuthorize(AUTH_ALL)
    @GetMapping
    public List<GuideDto> findAllRoles() {
        return guideService.findAll().stream().map(Guide::convertToDto).collect(Collectors.toList());
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/{id}")
    public GuideDto getGuide(@PathVariable("id") Long id) throws NotFoundException {
        return guideService.getById(id).map(Guide::convertToDto).orElseThrow(() -> new NotFoundException("This guide not exist"));
    }

    @PreAuthorize(AUTH_ADMIN)
    @PostMapping
    public GuideDto newGuide(@RequestBody Guide newGuide) {
        return guideService.save(newGuide).convertToDto();
    }

    @PreAuthorize(AUTH_ADMIN)
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

    @PreAuthorize(AUTH_ADMIN)
    @DeleteMapping("/{id}")
    public void deleteGuide(@PathVariable("id") Long id) {
        guideService.deleteById(id);
    }
}
