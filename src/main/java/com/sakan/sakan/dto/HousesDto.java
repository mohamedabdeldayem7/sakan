package com.sakan.sakan.dto;


import jakarta.persistence.Column;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HousesDto {

    private UUID id;

    private String location;

    private String image;

    private Integer numberOfRooms;

    private String kitchens;

    private String bathrooms;

    private Double minPrice;

    private Double maxPrice;

    private String description;

    private Integer rate;

}
