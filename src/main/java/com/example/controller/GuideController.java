package com.example.controller;

import com.example.controller.exception.NoEntityException;
import com.example.model.Guide;
import com.example.service.GuideService;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.security.SecurityConstants.*;

@Controller
@RequestMapping("/api/v1/guides")
public class GuideController {

    private GuideService guideService;
    private RoleService roleService;

    @Autowired
    public GuideController(GuideService guideService, RoleService roleService) {
        this.guideService = guideService;
        this.roleService = roleService;
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping
    public String findAllGuides(Model model) {
        model.addAttribute("guides", guideService.findAll());
        return "guide/guides";
    }

    @PreAuthorize(AUTH_ALL)
    @GetMapping("/{id}")
    public String getGuide(@PathVariable("id") Long id, Model model) throws NoEntityException {
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
    public String newGuide(@ModelAttribute("guide") Guide guide) throws NoEntityException {
        guide.setRole(roleService.findByName(GUIDE).orElseThrow(NoEntityException::new));
        guideService.save(guide);
        return "redirect:/api/v1/guides";
    }

    @PreAuthorize(AUTH_GUIDE)
    @GetMapping("/{id}/edit")
    public String editGuide(@PathVariable("id") Long id, Model model) throws NoEntityException {
        model.addAttribute("guide", guideService.getById(id).orElseThrow(NoEntityException::new));
        return "guide/edit";
    }


    @PreAuthorize(AUTH_GUIDE)
    @PutMapping("/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("guide") Guide guide) throws NoEntityException {
        guide.setRole(roleService.findByName(GUIDE).orElseThrow(NoEntityException::new));
        guideService.save(guide);
        return "redirect:/api/v1/guides";
    }

    @PreAuthorize(AUTH_ADMIN)
    @DeleteMapping("/{id}")
    public String deleteGuide(@PathVariable("id") Long id) {
        guideService.deleteById(id);
        return "redirect:/api/v1/guides";
    }
}
