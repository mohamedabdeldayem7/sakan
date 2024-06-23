package com.sakan.sakan.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDetailsRequest {

    private String email;

    private String firstname;

    private String lastname;

    private String birthdate;

    private String city;

}
