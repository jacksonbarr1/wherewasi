package com.wherewasi.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
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
