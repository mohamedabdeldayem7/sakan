package com.sakan.sakan.service.Impl;

import com.sakan.sakan.dto.HouseRequestDto;
import com.sakan.sakan.dto.HouseResponseDto;
import com.sakan.sakan.dto.HousesDto;
import com.sakan.sakan.entities.House;
import com.sakan.sakan.exception.HouseNotFoundException;
import com.sakan.sakan.mapper.HouseMapper;
import com.sakan.sakan.repository.HouseRepository;
import com.sakan.sakan.service.FileService;
import com.sakan.sakan.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final HouseMapper mapper;

    private final FileService fileService;

    @Value("${project.house}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public HouseResponseDto addHouse(
            HouseRequestDto requestDto,
            MultipartFile file
    ) throws IOException {

        String uploadedFile = fileService.uploadFile(path, file);

        House house = mapper.houseRequestDtoToHouse(requestDto);

        house.setImage(uploadedFile);
        house.setCreatedAt(LocalDateTime.now());

        House savedHouse = houseRepository.save(house);

        HouseResponseDto responseDto = mapper.houseToHouseResponseDto(savedHouse);
        responseDto.setImage(savedHouse.getImage());

        return responseDto;
    }

    @Override
    public List<HousesDto> getAllHouses() {

        List<House> houses = houseRepository.findAll();

        List<HousesDto> responseListDtos = houses.stream()
                .map(mapper::houseToHouseResponseListDto)
                .toList();

        for(int i=0 ; i<houses.size() ; i++){

            String imageUrl = baseUrl + "/file/" + houses.get(i).getImage();

            responseListDtos.get(i)
                    .setImage(imageUrl);
        }
        return responseListDtos;
    }

    @Override
    public List<HousesDto> getAllHousesByLocation(String location) {

        List<House> houses = houseRepository.findAllByLocation(location);

        List<HousesDto> responseListDtos = houses.stream()
                .map(mapper::houseToHouseResponseListDto)
                .toList();

        for(int i=0 ; i<houses.size() ; i++){

            String imageUrl = baseUrl + "/file/" + houses.get(i).getImage();

            responseListDtos.get(i)
                    .setImage(imageUrl);
        }
        return responseListDtos;
    }

    @Override
    public HouseResponseDto getHouseById(UUID id) {

        House house = houseRepository.findById(id)
                .orElseThrow(() -> new HouseNotFoundException("House not found!, please enter a valid id!"));

        HouseResponseDto responseDto = mapper.houseToHouseResponseDto(house);

        String imageUrl = baseUrl + "/file/" + house.getImage();

        responseDto.setImage(imageUrl);

        return responseDto;
    }

}
