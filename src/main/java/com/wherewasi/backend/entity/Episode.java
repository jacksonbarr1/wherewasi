package com.wherewasi.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
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
