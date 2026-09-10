package com.newinntech.voting_system.dto;

import com.newinntech.voting_system.entity.Voter.VoterBuilder;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoterResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Integer voteCount;
}