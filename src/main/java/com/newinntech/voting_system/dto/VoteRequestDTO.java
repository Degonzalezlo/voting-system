package com.newinntech.voting_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteRequestDTO {

    @NotNull(message = "El ID del votante es obligatorio")
    private Long voterId;

    @NotNull(message = "El ID del candidato es obligatorio")
    private Long candidateId;
}