package com.sakan.sakan.service.Impl;

import com.sakan.sakan.auth.entities.User;
import com.sakan.sakan.auth.repositories.UserRepository;
import com.sakan.sakan.dto.ProfileDetailsDto;
import com.sakan.sakan.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public ProfileDetailsDto getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        ProfileDetailsDto profileDetails = ProfileDetailsDto.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .Birthdate(user.getBirthdate())
                .city(user.getCity())
                .build();

        String imageUrl = baseUrl + "/profile-image/" + user.getProfileImage();

        profileDetails.setProfileImage(imageUrl);

        return profileDetails;
    }
}
