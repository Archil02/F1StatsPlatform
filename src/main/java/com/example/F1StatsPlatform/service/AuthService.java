package com.example.F1StatsPlatform.service;

import com.example.F1StatsPlatform.dto.AuthResponse;
import com.example.F1StatsPlatform.dto.LoginRequest;
import com.example.F1StatsPlatform.dto.RegisterRequest;
import com.example.F1StatsPlatform.entity.AppUser;
import com.example.F1StatsPlatform.entity.Role;
import com.example.F1StatsPlatform.exception.DuplicateEmailException;
import com.example.F1StatsPlatform.repository.AppUserRepository;
import com.example.F1StatsPlatform.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("ამ ემაილით მომხმარებელი უკვე რეგისტრირებულია");
        }

        AppUser appUser = AppUser.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        appUserRepository.save(appUser);

        String token = jwtService.generateToken(toUserDetails(appUser));
        return buildResponse(appUser, token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        AppUser appUser = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("მომხმარებელი ვერ მოიძებნა ავთენტიფიკაციის შემდეგ"));

        String token = jwtService.generateToken(toUserDetails(appUser));
        return buildResponse(appUser, token);
    }

    private UserDetails toUserDetails(AppUser appUser) {
        return User.builder()
                .username(appUser.getEmail())
                .password(appUser.getPasswordHash())
                .authorities("ROLE_" + appUser.getRole().name())
                .build();
    }

    private AuthResponse buildResponse(AppUser appUser, String token) {
        return AuthResponse.builder()
                .token(token)
                .email(appUser.getEmail())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .role(appUser.getRole().name())
                .build();
    }
}