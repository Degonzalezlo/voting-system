package com.newinntech.voting_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.newinntech.voting_system.dto.VoterRequestDTO;
import com.newinntech.voting_system.dto.VoterResponseDTO;
import com.newinntech.voting_system.service.VoterService;



@RestController
@RequestMapping("/voters")
@RequiredArgsConstructor
public class VoterController {

    private final VoterService voterService;

    @PostMapping
    public ResponseEntity<VoterResponseDTO> createVoter(@Valid @RequestBody VoterRequestDTO request) {
        return new ResponseEntity<>(voterService.createVoter(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<VoterResponseDTO>> getAllVoters(
        @RequestParam(required = false) String name,
        @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
    return ResponseEntity.ok(voterService.getAllVoters(name, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoterResponseDTO> getVoterById(@PathVariable Long id) {
        return ResponseEntity.ok(voterService.getVoterById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoter(@PathVariable Long id) {
        voterService.deleteVoter(id);
        return ResponseEntity.noContent().build();
    }
}