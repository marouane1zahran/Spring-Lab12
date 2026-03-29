package com.security.spring_jwt;

import com.security.spring_jwt.entities.AppUser;
import com.security.spring_jwt.entities.Role;
import com.security.spring_jwt.repositories.AppUserRepository;
import com.security.spring_jwt.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crée les rôles s'ils n'existent pas
        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));
        Role roleUser = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

        // Crée l'utilisateur "admin" avec le mot de passe "1234" s'il n'existe pas
        if (userRepository.findByUsername("admin").isEmpty()) {
            AppUser admin = AppUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("1234")) // Le mot de passe est crypté en BDD !
                    .active(true)
                    .roles(List.of(roleAdmin, roleUser))
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Utilisateur 'admin' créé avec succès (Mot de passe: 1234)");
        }
    }
}