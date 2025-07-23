package com.wherewasi.backend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "genre")
public class Network {
    @Id
    Long id;
    private String logoPath;
    private String name;
    private String originCountry;
}
