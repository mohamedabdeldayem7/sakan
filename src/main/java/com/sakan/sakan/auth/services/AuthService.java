package com.sakan.sakan.auth.services;

import com.sakan.sakan.auth.entities.User;
import com.sakan.sakan.auth.entities.UserRole;
import com.sakan.sakan.auth.repositories.UserRepository;
import com.sakan.sakan.auth.utils.AuthResponse;
import com.sakan.sakan.auth.utils.LoginRequest;
import com.sakan.sakan.auth.utils.ProfileDetailsRequest;
import com.sakan.sakan.auth.utils.RegisterRequest;
import com.sakan.sakan.exception.UserExistsException;
import com.sakan.sakan.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final FileService fileService;

    @Value("${project.profile}")
    private String path;

    /*
     * REGISTRATION of NEW USER
     * build user object using request object
     * save user in DB
     * generate JWT/refreshToken and send response
     */
    public AuthResponse register(RegisterRequest request) {

        Optional<User> existUser = userRepository.findByEmail(request.getEmail());

        if(existUser.isPresent()){
            throw new UserExistsException("This email or phone number is already exist!");
        }
        var user = User.builder()
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        User updatedUser = userRepository.save(user);

        var jwt = jwtService.generateToken(updatedUser);

        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public String completeProfile(
            ProfileDetailsRequest request,
            MultipartFile file
    ) throws IOException {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + request.getEmail()));

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setBirthdate(request.getBirthdate());
        user.setCity(request.getCity());

        if(file.isEmpty()){

        }

        String uploadedFile = fileService.uploadFile(path, file);

        user.setProfileImage(uploadedFile);

        User updatedUser = userRepository.save(user);

        return "Details added successfully!";

    }
    /*
     * LOGIN USER
     * using AuthenticationManager to authenticate user
     * fetch user details
     * generate JWT/refreshToken and send response
     */
    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + request.getEmail()));
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        return AuthResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

}
