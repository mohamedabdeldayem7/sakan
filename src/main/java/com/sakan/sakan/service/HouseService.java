package com.sakan.sakan.service;

import com.sakan.sakan.dto.HouseRequestDto;
import com.sakan.sakan.dto.HouseResponseDto;
import com.sakan.sakan.dto.HouseResponseListDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface HouseService {

    HouseResponseDto addHouse(HouseRequestDto requestDto, List<MultipartFile> files) throws IOException;

    List<HouseResponseListDto> getAllHousesByLocation(String location);

    HouseResponseDto getHouseById(UUID id);

}
