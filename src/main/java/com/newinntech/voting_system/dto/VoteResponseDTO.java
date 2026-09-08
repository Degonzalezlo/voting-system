package com.newinntech.voting_system.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteResponseDTO {

    private Long voteId;
    private Long voterId;
    private String voterName;
    private Long candidateId;
    private String candidateName;
    private LocalDateTime votedAt;
}