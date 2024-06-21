package com.sakan.sakan.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HouseResponseDto {

    private UUID id;

    private String location;

    private List<String> images;

    private Integer numberOfRooms;

    private String kitchens;

    private String bathrooms;

    private Double price;

    private Integer rate;

    private String description;

    private LocalDateTime createdAt;

}
