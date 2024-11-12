package com.codecool.solarwatch;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.AppUser;
import com.codecool.solarwatch.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInitializer(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        AppUser admin = new AppUser("admin", passwordEncoder.encode("admin"), Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
        appUserRepository.save(admin);

        System.out.println("Database initialized");
    }
}
