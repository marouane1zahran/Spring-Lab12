package com.security.spring_jwt.web;

import com.security.spring_jwt.dto.LoginRequest;
import com.security.spring_jwt.dto.LoginResponse;
import com.security.spring_jwt.jwt.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest credentials) {
        System.out.println("Username reçu : " + credentials.getUsername());
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
        );
        UserDetails user = userDetailsService.loadUserByUsername(credentials.getUsername());
        String generatedToken = tokenService.generateToken(user.getUsername());

        return ResponseEntity.ok(new LoginResponse(generatedToken, user.getUsername(), "Bearer"));
    }
}