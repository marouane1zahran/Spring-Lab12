package com.security.spring_jwt.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProtectedResourceController {

    @GetMapping("/user/profile")
    public ResponseEntity<Map<String, Object>> getUserInfo(Authentication authentication) {
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("message", "Données utilisateur récupérées avec succès.");
        profileData.put("username", authentication.getName());
        return ResponseEntity.ok(profileData);
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<Map<String, String>> getAdminPanel() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Accès autorisé au tableau de bord administrateur.");
        return ResponseEntity.ok(response);
    }
}