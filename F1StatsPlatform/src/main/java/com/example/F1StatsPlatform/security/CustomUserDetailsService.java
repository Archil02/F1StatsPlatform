package com.example.F1StatsPlatform.security;

import com.example.F1StatsPlatform.entity.AppUser;
import com.example.F1StatsPlatform.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("მომხმარებელი ვერ მოიძებნა: " + email));

        return User.builder()
                .username(appUser.getEmail())
                .password(appUser.getPasswordHash())
                .disabled(!Boolean.TRUE.equals(appUser.getEnabled()))
                .authorities("ROLE_" + appUser.getRole().name())
                .build();
    }
}