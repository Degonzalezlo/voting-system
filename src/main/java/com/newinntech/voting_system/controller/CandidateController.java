package com.newinntech.voting_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.newinntech.voting_system.dto.CandidateRequestDTO;
import com.newinntech.voting_system.dto.CandidateResponseDTO;
import com.newinntech.voting_system.service.CandidateService;



@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<CandidateResponseDTO> createCandidate(@Valid @RequestBody CandidateRequestDTO request) {
        return new ResponseEntity<>(candidateService.createCandidate(request), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<CandidateResponseDTO>> getAllCandidates(
        @RequestParam(required = false) String name,
        @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
            
        return ResponseEntity.ok(candidateService.getAllCandidates(name, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> getCandidateById(@PathVariable Long id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }
}