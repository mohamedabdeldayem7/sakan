package com.sakan.sakan.service.Impl;

import com.sakan.sakan.dto.HouseRequestDto;
import com.sakan.sakan.dto.HouseResponseDto;
import com.sakan.sakan.dto.HouseResponseListDto;
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

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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
            List<MultipartFile> files
    ) throws IOException {

        List<String> uploadedFiles = new ArrayList<>();

        for(MultipartFile file : files){
            String uploadedFile = fileService.uploadFile(path, file);

            uploadedFiles.add(uploadedFile);
        }

        House house = mapper.houseRequestDtoToHouse(requestDto);

        house.setImages(uploadedFiles);
        house.setCreatedAt(LocalDateTime.now());

        House savedHouse = houseRepository.save(house);

        HouseResponseDto responseDto = mapper.houseToHouseResponseDto(savedHouse);
        responseDto.setImages(getImagesUrls(savedHouse.getImages()));

        return responseDto;
    }

    @Override
    public List<HouseResponseListDto> getAllHousesByLocation(String location) {

        List<House> houses = houseRepository.findAllByLocation(location);

        List<HouseResponseListDto> responseListDtos = houses.stream()
                .map(mapper::houseToHouseResponseListDto)
                .toList();

        for(int i=0 ; i<houses.size() ; i++){
            responseListDtos.get(i)
                    .setImages(
                            getImagesUrls(
                                    houses.get(i).getImages()
                            )
                    );
        }
        return responseListDtos;
    }

    @Override
    public HouseResponseDto getHouseById(UUID id) {

        House house = houseRepository.findById(id)
                .orElseThrow(() -> new HouseNotFoundException("House not found!, please enter a valid id!"));

        HouseResponseDto responseDto = mapper.houseToHouseResponseDto(house);

        responseDto.setImages(getImagesUrls(house.getImages()));

        return responseDto;
    }

    private List<String> getImagesUrls(List<String> imagesNames){

        List<String> imagesUrls = new ArrayList<>();

        for(String image : imagesNames){

            String imageUrl = baseUrl + "/file/" + image;

            imagesUrls.add(imageUrl);
        }

        return imagesUrls;
    }
}
