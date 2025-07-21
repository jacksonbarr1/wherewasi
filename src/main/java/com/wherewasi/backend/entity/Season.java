package com.wherewasi.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "season")
public class Season {
    @Id
    private Long id;
    private String seasonName;
    private Integer seasonNumber;
    private Integer episodeCount;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Episode> episodes;
}
