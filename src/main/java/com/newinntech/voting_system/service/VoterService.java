package com.newinntech.voting_system.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newinntech.voting_system.dto.VoterRequestDTO;
import com.newinntech.voting_system.dto.VoterResponseDTO;
import com.newinntech.voting_system.entity.Voter;
import com.newinntech.voting_system.exception.ResourceNotFoundException;
import com.newinntech.voting_system.exception.VotingException;
import com.newinntech.voting_system.repository.CandidateRepository;
import com.newinntech.voting_system.repository.VoterRepository;



@Service
@RequiredArgsConstructor
public class VoterService {

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

    @Transactional
    public VoterResponseDTO createVoter(VoterRequestDTO request) {
        if (voterRepository.existsByEmail(request.getEmail())) {
            throw new VotingException("Ya existe un votante registrado con el email: " + request.getEmail());
        }
        // Validar si ya existe un candidato con el mismo nombre
        if(candidateRepository.existsByName(request.getName())) {
            throw new VotingException("Ya existe un candidato registrado con el nombre: " + request.getName());
        }

        Voter voter = Voter.builder()
                .name(request.getName())
                .email(request.getEmail())
                .hasVoted(false)
                .build();

        Voter savedVoter = voterRepository.save(voter);
        return mapToResponse(savedVoter);
    }

    @Transactional(readOnly = true)
    public Page<VoterResponseDTO> getAllVoters(String name, Pageable pageable) { 
        if (name != null && !name.isBlank()) {
            return voterRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
        }
        return voterRepository.findAll(pageable)
            .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public VoterResponseDTO getVoterById(Long id) {
        Voter voter = voterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Votante no encontrado con ID: " + id));
        return mapToResponse(voter);
    }

    @Transactional
    public void deleteVoter(Long id) {
        if (!voterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Votante no encontrado con ID: " + id);
        }
        voterRepository.deleteById(id);
    }

    private VoterResponseDTO mapToResponse(Voter voter) {
        return VoterResponseDTO.builder()
                .id(voter.getId())
                .name(voter.getName())
                .email(voter.getEmail())
                .hasVoted(voter.getHasVoted())
                .build();
    }
}