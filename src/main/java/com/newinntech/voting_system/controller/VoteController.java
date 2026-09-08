package com.newinntech.voting_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.newinntech.voting_system.dto.VoteRequestDTO;
import com.newinntech.voting_system.dto.VoteResponseDTO;
import com.newinntech.voting_system.dto.VotingStatisticsDTO;
import com.newinntech.voting_system.service.VoteService;

import java.util.List;

@RestController
@RequestMapping("/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteResponseDTO> castVote(@Valid @RequestBody VoteRequestDTO request) {
        return new ResponseEntity<>(voteService.castVote(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VoteResponseDTO>> getAllVotes() {
        return ResponseEntity.ok(voteService.getAllVotes());
    }

    @GetMapping("/statistics")
    public ResponseEntity<VotingStatisticsDTO> getStatistics() {
        return ResponseEntity.ok(voteService.getStatistics());
    }
}