package com.sakan.sakan.dto;


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

    private Double price;

    private Integer rate;

}
