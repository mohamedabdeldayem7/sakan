package com.sakan.sakan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakan.sakan.dto.HouseRequestDto;
import com.sakan.sakan.dto.HouseResponseDto;
import com.sakan.sakan.dto.HouseResponseListDto;
import com.sakan.sakan.service.HouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping("/add")
    public ResponseEntity<HouseResponseDto> addHouse(
            @RequestPart @Valid String houseRequestDto,
            @RequestPart List<MultipartFile> files
            ) throws IOException {

        HouseRequestDto requestDto = convertToHouseRequestDto(houseRequestDto);

        HouseResponseDto responseDto = houseService.addHouse(requestDto, files);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/all/{location}")
    public ResponseEntity<?> getAllHousesByLocation(
            @PathVariable String location
    ){

        List<HouseResponseListDto> responseListDtoList = houseService.getAllHousesByLocation(location);

        return ResponseEntity.ok(responseListDtoList);
    }

    @GetMapping("/all")
    public ResponseEntity<HouseResponseDto> getAllHousesByLocation(
            @RequestParam(value="id") UUID id
    ){

        HouseResponseDto responseDto = houseService.getHouseById(id);

        return ResponseEntity.ok(responseDto);
    }

    private HouseRequestDto convertToHouseRequestDto(String dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(dto, HouseRequestDto.class);
    }

}
