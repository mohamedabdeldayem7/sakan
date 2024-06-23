package com.sakan.sakan.controller;

import com.sakan.sakan.dto.ProfileDetailsDto;
import com.sakan.sakan.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile-details")
    public ResponseEntity<ProfileDetailsDto> getProfileByEmail(
            @RequestParam(value="email") String email
    ){

        ProfileDetailsDto responseDto = profileService.getProfile(email);

        return ResponseEntity.ok(responseDto);
    }

}
