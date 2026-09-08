package com.newinntech.voting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newinntech.voting_system.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    // Verifica si un voter_id ya registó un voto en la tabla votes
    boolean existsByVoterId(Long voterId);
}
