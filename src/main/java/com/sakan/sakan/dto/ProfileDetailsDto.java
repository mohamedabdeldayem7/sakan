package com.sakan.sakan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDetailsDto {

    private String phoneNumber;

    @NotBlank(message = "The field 'email' can't be empty")
    @Email(message = "Please enter correct email format!")
    private String email;

    private String firstname;

    private String lastname;

    private String Birthdate;

    private String city;

    private String profileImage;

}
