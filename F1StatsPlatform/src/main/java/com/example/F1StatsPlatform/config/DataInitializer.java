package com.example.F1StatsPlatform.config;

import com.example.F1StatsPlatform.entity.AppUser;
import com.example.F1StatsPlatform.entity.Role;
import com.example.F1StatsPlatform.repository.AppUserRepository;
import com.example.F1StatsPlatform.sync.DataSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final DataSyncService dataSyncService;

    @Override
    public void run(ApplicationArguments args) {
        createAdminIfNotExists();
        syncData();
    }

    private void createAdminIfNotExists() {
        String adminEmail = "admin@f1stats.com";
        if (!appUserRepository.existsByEmail(adminEmail)) {
            AppUser admin = AppUser.builder()
                    .email(adminEmail)
                    .firstName("Admin")
                    .lastName("F1Stats")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            appUserRepository.save(admin);
        } else {
            System.out.println("✅ Admin მომხმარებელი უკვე არსებობს");
        }
    }

    private void syncData() {
        try {
            System.out.println("🔄 F1 მონაცემების sync იწყება...");
            int season = Year.now().getValue();
            dataSyncService.syncAll(season);
            System.out.println("✅ F1 მონაცემების sync დასრულდა (season={})" + season);
        } catch (Exception e) {
            System.out.println("❌ Sync ვერ შესრულდა: {}" + e.getMessage());
        }
    }
}
