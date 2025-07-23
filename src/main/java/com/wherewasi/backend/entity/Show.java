package com.wherewasi.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "show")
public class Show {
    @Id
    private Long id;
    private Boolean adult;
    private String homepage;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String overview;
    private LocalDate firstAirDate;
    private LocalDate lastAirDate;
    private String posterPath;
    private String backdropPath;
    private Float popularity;
    private Float voteAverage;
    private Integer voteCount;
    private Boolean isInProduction;
    private String status;
    private String tagline;
    private String type;
    private Integer numberOfEpisodes;
    private Integer numberOfSeasons;

    private LocalDateTime lastApiFetchTimestamp;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "show_season",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "season_id")
    )
    @ToString.Exclude
    private List<Season> seasons;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "show_genre",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @ToString.Exclude
    private List<Genre> genres;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "show_creator",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "creator_id")
    )
    @ToString.Exclude
    private List<Creator> creators;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "show_network",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "network_id")
    )
    @ToString.Exclude
    private List<Network> networks;
}
