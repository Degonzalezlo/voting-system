package com.newinntech.voting_system.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newinntech.voting_system.dto.CandidateRequestDTO;
import com.newinntech.voting_system.dto.CandidateResponseDTO;
import com.newinntech.voting_system.entity.Candidate;
import com.newinntech.voting_system.exception.ResourceNotFoundException;
import com.newinntech.voting_system.exception.VotingException;
import com.newinntech.voting_system.repository.CandidateRepository;
import com.newinntech.voting_system.repository.VoterRepository;


@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;

    @Transactional
    public CandidateResponseDTO createCandidate(CandidateRequestDTO request) {
        if (candidateRepository.existsByName(request.getName())) {
            throw new VotingException("Ya existe un candidato registrado con el nombre: " + request.getName());
        }

        if(voterRepository.findAll().stream().anyMatch(voter -> voter.getName().equalsIgnoreCase(request.getName()))) {
            throw new VotingException("La persona " + request.getName() + "' ya está registrada como Votante.");
        }

        Candidate candidate = Candidate.builder()
                .name(request.getName())
                .party(request.getParty())
                .votes(0)
                .build();

        Candidate savedCandidate = candidateRepository.save(candidate);
        return mapToResponse(savedCandidate);
    }

   @Transactional(readOnly = true)
   public Page<CandidateResponseDTO> getAllCandidates(String name, Pageable pageable) {
    if (name != null && !name.isBlank()) {
        return candidateRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
        }
        return candidateRepository.findAll(pageable)
            .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public CandidateResponseDTO getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con ID: " + id));
        return mapToResponse(candidate);
    }

    @Transactional
    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con ID: " + id));
        candidateRepository.delete(candidate);
    }


    private CandidateResponseDTO mapToResponse(Candidate candidate) {
        return CandidateResponseDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .party(candidate.getParty())
                .votes(candidate.getVotes())
                .build();
    }
}
