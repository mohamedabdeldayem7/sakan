package com.sakan.sakan.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class HouseDescription {

    private Integer numberOfRooms;

    private Integer numberOfKitchens;

    private Integer numberOfBathrooms;

    private Double price;

    private String otherDescription;

}
