package com.wherewasi.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private String name;
    private Integer seasonNumber;
    private Integer episodeCount;
    private LocalDate airDate;
    private String posterPath;
    @Column(columnDefinition = "TEXT")
    private String overview;
    private Float voteAverage;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Episode> episodes;
}
