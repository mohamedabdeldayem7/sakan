package com.sakan.sakan.service;

import com.sakan.sakan.dto.ProfileDetailsDto;

public interface ProfileService {

    ProfileDetailsDto getProfile(String email);

}
