package com.example.repository;

import com.example.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    Optional<Guide> findByUsername(String username);
}
