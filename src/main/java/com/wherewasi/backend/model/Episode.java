package com.wherewasi.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "episode")
public class Episode {
    @Id
    private Long id;
    private Integer episodeNumber;
    private Date airDate;
    private String name;
    private String overview;
    private Float voteAverage;
    private Integer voteCount;

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;
}
