package com.newinntech.voting_system.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.newinntech.voting_system.entity.Voter;

import java.util.Optional;


public interface VoterRepository extends JpaRepository<Voter, Long> {
    
    // Para validar que no existan dos votantes con el mismo email
    boolean existsByEmail(String email);
    
    // Para buscar un votante por su email
    Optional<Voter> findByEmail(String email);

    Page<Voter> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
