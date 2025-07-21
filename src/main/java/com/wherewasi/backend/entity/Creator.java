package com.wherewasi.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "creator")
public class Creator {
    @Id
    private Long id;
    private String name;
    private String imagePath;
}
