package com.example.F1StatsPlatform.controller;

import com.example.F1StatsPlatform.entity.AppUser;
import com.example.F1StatsPlatform.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AppUserRepository appUserRepository;

    @GetMapping("/me/subscription")
    public ResponseEntity<Map<String, Boolean>> getSubscription(Authentication auth) {
        AppUser user = appUserRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("მომხმარებელი ვერ მოიძებნა"));
        return ResponseEntity.ok(Map.of("subscribed", user.getSubscribedToNotifications()));
    }

    @PutMapping("/me/subscription")
    public ResponseEntity<Map<String, Boolean>> updateSubscription(
            Authentication auth,
            @RequestBody Map<String, Boolean> body) {

        AppUser user = appUserRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("მომხმარებელი ვერ მოიძებნა"));

        boolean subscribed = Boolean.TRUE.equals(body.get("subscribed"));
        user.setSubscribedToNotifications(subscribed);
        appUserRepository.save(user);

        return ResponseEntity.ok(Map.of("subscribed", subscribed));
    }
}