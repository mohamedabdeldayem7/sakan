package com.sakan.sakan.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HouseRequestDto {

    @NotBlank(message = "Location field is required!")
    private String location;

    private Integer numberOfRooms;

    private String kitchens;

    private String bathrooms;

    private Double price;

    private String description;

}
