package com.sakan.sakan.dto;


import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HouseResponseListDto {

    private UUID id;

    private String location;

    private List<String> images;

    private Integer numberOfRooms;

    private Double price;

    private Integer rate;

}
