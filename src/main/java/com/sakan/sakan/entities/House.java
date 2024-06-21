package com.sakan.sakan.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class House extends BaseEntity{

    @Id
    @GeneratedValue
    private Integer id;

    private String location;

    @ElementCollection
    @CollectionTable(name = "house_images")
    private List<String> images;

    @Embedded
    private HouseDescription description;
}
