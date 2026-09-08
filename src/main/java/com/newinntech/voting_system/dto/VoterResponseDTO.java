package com.newinntech.voting_system.dto;

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
    private Boolean hasVoted;
}