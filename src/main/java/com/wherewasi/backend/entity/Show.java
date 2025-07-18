package com.wherewasi.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "show")
public class Show {
    @Id
    private Long id;
    private String name;
    private String overview;
    private Date firstAirDate;
    private Date lastAirDate;
    private String posterPath;
    private String backdropPath;
    private Float popularity;
    private Float voteAverage;
    private Integer voteCount;

    @ManyToMany
    @JoinTable(
            name = "show_season",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "season_id")
    )
    private List<Season> seasons;

    @ManyToMany
    @JoinTable(
            name = "show_genre",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "show_creator",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "creator_id")
    )
    private List<Creator> creators;
}
