package com.sakan.sakan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakan.sakan.dto.HouseRequestDto;
import com.sakan.sakan.dto.HouseResponseDto;
import com.sakan.sakan.dto.HousesDto;
import com.sakan.sakan.service.HouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

//    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST}, value = "/add-house",
//            consumes= MediaType.ALL_VALUE)
    @PostMapping(value = "/add-house", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HouseResponseDto> addHouse(
            @RequestPart  String houseDto,
            @RequestPart MultipartFile file
            ) throws IOException {

        HouseRequestDto requestDto = convertToHouseRequestDto(houseDto);

        HouseResponseDto responseDto = houseService.addHouse(requestDto, file);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/all-houses")
    public ResponseEntity<?> getAllHouses(){

        List<HousesDto> responseListDtoList = houseService.getAllHouses();

        return ResponseEntity.ok(responseListDtoList);
    }

    @GetMapping("/all/{location}")
    public ResponseEntity<?> getAllHousesByLocation(
            @PathVariable String location
    ){

        List<HousesDto> responseListDtoList = houseService.getAllHousesByLocation(location);

        return ResponseEntity.ok(responseListDtoList);
    }

    @GetMapping
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
