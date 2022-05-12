package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Guide;
import com.example.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.security.SecurityConstants.AUTH_ADMIN;
import static com.example.security.SecurityConstants.AUTH_ALL;

@Controller
@RequestMapping("/api/v1/guides")
public class GuideController {

    private GuideService guideService;

    @Autowired
    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping
    public String findAllRoles(Model model) {
        model.addAttribute("guides", guideService.findAll());
        return "guide/guides";
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/{id}")
    public String getRole(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("guide", guideService.getById(id).orElseThrow(NoEntityException::new));
        return "guide/show";
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping("/new")
    public String create(@ModelAttribute("guide") Guide guide) {
        return "guide/new";
    }

    @PreAuthorize(AUTH_ADMIN)
    @PostMapping
    public String newRole(@ModelAttribute("guide") Guide guide) {
        guideService.save(guide);
        return "redirect:/api/v1/guides";
    }

    @PreAuthorize(AUTH_ADMIN)
    @GetMapping("/{id}/edit")
    public String editRole(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("guide", guideService.getById(id).orElseThrow(NoEntityException::new));
        return "guide/edit";
    }


    @PreAuthorize(AUTH_ADMIN)
    @PutMapping("/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("guide") Guide guide) {
        guideService.save(guide);
        return "redirect:/api/v1/guides";
    }

    @PreAuthorize(AUTH_ADMIN)
    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable("id") Long id) {
        guideService.deleteById(id);
        return "redirect:/api/v1/guides";
    }
}
