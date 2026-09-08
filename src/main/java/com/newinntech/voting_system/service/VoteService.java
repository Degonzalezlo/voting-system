package com.newinntech.voting_system.service;

import com.newinntech.voting_system.dto.VoteRequestDTO;
import com.newinntech.voting_system.dto.VoteResponseDTO;
import com.newinntech.voting_system.dto.VotingStatisticsDTO;
import com.newinntech.voting_system.entity.Candidate;
import com.newinntech.voting_system.entity.Vote;
import com.newinntech.voting_system.entity.Voter;
import com.newinntech.voting_system.exception.ResourceNotFoundException;
import com.newinntech.voting_system.exception.VotingException;
import com.newinntech.voting_system.repository.CandidateRepository;
import com.newinntech.voting_system.repository.VoteRepository;
import com.newinntech.voting_system.repository.VoterRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

    @Transactional
    public VoteResponseDTO castVote(VoteRequestDTO request) {
        // 1. Validar existencia del votante
        Voter voter = voterRepository.findById(request.getVoterId())
                .orElseThrow(() -> new ResourceNotFoundException("Votante no encontrado con ID: " + request.getVoterId()));

        // 2. Validar regla de negocio: ¿Ya ha votado el votante?
        if (Boolean.TRUE.equals(voter.getHasVoted())) {
            throw new VotingException("El votante con ID " + voter.getId() + " ya ha registrado su voto previamente.");
        }

        // 3. Validar existencia del candidato
        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con ID: " + request.getCandidateId()));

        // 4. Actualizar estado del votante e incrementar votos del candidato
        voter.setHasVoted(true);
        candidate.setVotes(candidate.getVotes() + 1);

        voterRepository.save(voter);
        candidateRepository.save(candidate);

        // 5. Crear y guardar el registro del voto
        Vote vote = Vote.builder()
                .voter(voter)
                .candidate(candidate)
                .build();

        Vote savedVote = voteRepository.save(vote);

        return mapToResponse(savedVote);
    }

    @Transactional(readOnly = true)
    public List<VoteResponseDTO> getAllVotes() {
        return voteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public VotingStatisticsDTO getStatistics() {
        long totalVotes = voteRepository.count();
        List<Candidate> candidates = candidateRepository.findAllByOrderByVotesDesc();

        List<VotingStatisticsDTO.CandidateStatDTO> stats = candidates.stream().map(c -> {
            double percentage = totalVotes > 0 ? (c.getVotes() * 100.0) / totalVotes : 0.0;
            return VotingStatisticsDTO.CandidateStatDTO.builder()
                    .candidateId(c.getId())
                    .candidateName(c.getName())
                    .party(c.getParty())
                    .votes(c.getVotes())
                    .percentage(Math.round(percentage * 100.0) / 100.0) // Redondeo a 2 decimales
                    .build();
        }).toList();

        return VotingStatisticsDTO.builder()
                .totalVotersWhoVoted(totalVotes)
                .totalVotesCast(totalVotes)
                .candidateStats(stats)
                .build();
    }

    private VoteResponseDTO mapToResponse(Vote vote) {
        return VoteResponseDTO.builder()
                .voteId(vote.getId())
                .voterId(vote.getVoter().getId())
                .voterName(vote.getVoter().getName())
                .candidateId(vote.getCandidate().getId())
                .candidateName(vote.getCandidate().getName())
                .votedAt(vote.getVotedAt())
                .build();
    }
        
        
}