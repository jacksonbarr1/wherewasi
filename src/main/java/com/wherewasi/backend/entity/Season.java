package com.wherewasi.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Episode> episodes = new ArrayList<>();

    public List<Episode> getEpisodes() {
        return Objects.requireNonNullElseGet(episodes, ArrayList::new);
    }
}
