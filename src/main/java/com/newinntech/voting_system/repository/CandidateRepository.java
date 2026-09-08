package com.newinntech.voting_system.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.newinntech.voting_system.entity.Candidate;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    
    //validar si ya existe un candidato con el mismo email/nombre
    boolean existsByName(String name);
    
    //obtener el ranking de candidatos ordenados por número de votos descendentemente (estadísticas)
    List<Candidate> findAllByOrderByVotesDesc();

    Page<Candidate> findByNameContainingIgnoreCase(String name, Pageable pageable);
}