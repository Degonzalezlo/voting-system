package com.newinntech.voting_system.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateResponseDTO {

    private Long id;
    private String name;
    private String party;
    private Integer votes;

}