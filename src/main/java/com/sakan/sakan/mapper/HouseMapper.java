package com.sakan.sakan.mapper;

import com.sakan.sakan.dto.HouseRequestDto;
import com.sakan.sakan.dto.HouseResponseDto;
import com.sakan.sakan.dto.HouseResponseListDto;
import com.sakan.sakan.entities.House;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface HouseMapper {

    House houseRequestDtoToHouse(HouseRequestDto requestDto);

    HouseResponseDto houseToHouseResponseDto(House house);

    HouseResponseListDto houseToHouseResponseListDto(House house);
}
