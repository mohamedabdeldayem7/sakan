package com.sakan.sakan.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HouseDescriptionDto {

    private Integer numberOfRooms;

    private Integer numberOfKitchens;

    private Integer numberOfBathrooms;

    private Double price;

    private String otherDescription;
}
