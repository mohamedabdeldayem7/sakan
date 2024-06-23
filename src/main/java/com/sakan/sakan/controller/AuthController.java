package com.sakan.sakan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakan.sakan.auth.entities.RefreshToken;
import com.sakan.sakan.auth.entities.User;
import com.sakan.sakan.auth.services.AuthService;
import com.sakan.sakan.auth.services.JwtService;
import com.sakan.sakan.auth.services.RefreshTokenService;
import com.sakan.sakan.auth.utils.*;
import com.sakan.sakan.dto.HouseRequestDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);


    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    public AuthController(AuthService authService,
                          RefreshTokenService refreshTokenService,
                          JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    // endpoint to register new user
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(RegisterRequest request) throws JsonProcessingException {

        logger.info(request.toString());
//        RegisterRequest registerRequest = convertToRegisterRequest(request);

        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/complete-profile",
            consumes= MediaType.MULTIPART_FORM_DATA_VALUE
    )    public ResponseEntity<String> completeProfile(
            @RequestPart String request,
            @RequestPart MultipartFile file
            ) throws IOException {

        ProfileDetailsRequest detailsRequest = convertToProfileDetailsRequest(request);

        return new ResponseEntity<>(authService.completeProfile(detailsRequest, file), HttpStatus.CREATED);
    }

    // endpoint to authenticate user for login
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest request) throws JsonProcessingException {

        logger.info(request.toString());

//        LoginRequest loginRequest = convertToLoginRequest(request);

        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        User user = refreshToken.getUser();

        String token = this.jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .token(token)
                .build());
    }

    private ProfileDetailsRequest convertToProfileDetailsRequest(String dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(dto, ProfileDetailsRequest.class);
    }

    private RegisterRequest convertToRegisterRequest(String dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(dto, RegisterRequest.class);
    }

    private LoginRequest convertToLoginRequest(String dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(dto, LoginRequest.class);
    }
}

