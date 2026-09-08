package com.newinntech.voting_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateRequestDTO {

    @NotBlank(message = "El nombre del candidato es obligatorio")
    private String name;

    private String party;
}