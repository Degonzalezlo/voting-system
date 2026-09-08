package com.newinntech.voting_system.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotingStatisticsDTO {
    private Long totalVotersWhoVoted;
    private Long totalVotesCast;
    private List<CandidateStatDTO> candidateStats;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CandidateStatDTO {
        private Long candidateId;
        private String candidateName;
        private String party;
        private Integer votes;
        private Double percentage;
    }
}
