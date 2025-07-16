package com.wherewasi.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "season")
public class Season {
    @Id
    private Long id;
    private Integer seasonNumber;
    private Integer episodeCount;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private Set<Episode> episodes;
}
