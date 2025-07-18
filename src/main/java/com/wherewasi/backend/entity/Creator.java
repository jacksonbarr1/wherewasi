package com.wherewasi.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "creator")
public class Creator {
    @Id
    private Long id;
    private String name;
    private String imagePath;
}
