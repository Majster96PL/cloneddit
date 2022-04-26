package com.example.cloneddit.clone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClonedditRepository extends JpaRepository<Cloneddit, Long> {
    Optional<Cloneddit> findByName(String clonedditName);
}
