package com.sakan.sakan.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class House{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String location;

    private String image;

    @Column(
            nullable = false
    )
    private Integer numberOfRooms;

    private String kitchens;

    private String bathrooms;

    @Column(nullable = false)
    private Double price;

    private Integer rate;

    @Column(nullable = false)
    private String description;


    private LocalDateTime createdAt;

}
