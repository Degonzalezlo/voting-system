package com.newinntech.voting_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "candidates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del candidato es obligatorio")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String party;

    @Column(nullable = false)
    @Builder.Default
    private Integer votes = 0;
}