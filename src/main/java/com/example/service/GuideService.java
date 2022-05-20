package com.example.service;

import com.example.model.Guide;
import com.example.model.User;
import com.example.model.exception.EntityNotFoundException;
import com.example.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GuideService {

    private GuideRepository guideRepository;

    @Autowired
    public GuideService(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    @Transactional
    public Guide save(Guide guide) {
        return guideRepository.save(guide);
    }

    @Transactional
    public Optional<Guide> getById(Long id) {
        return guideRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        guideRepository.deleteById(id);
    }

    @Transactional
    public Optional<Guide> findByUsername(String username) {
        return guideRepository.findByUsername(username);
    }

    @Transactional
    public List<Guide> findAll() {
        return guideRepository.findAll();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return guideRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with login " + username + " not found")).toSecurityGuide();
//    }
}
