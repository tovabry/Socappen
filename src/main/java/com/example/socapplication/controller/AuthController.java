package com.example.socapplication.controller;

import com.example.socapplication.model.dto.appUserDto.RegisterRequest;
import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.model.dto.authLogDto.CreateAuthLog;
import com.example.socapplication.model.dto.login.LoginRequest;
import com.example.socapplication.security.JwtUtil;
import com.example.socapplication.service.AppUserService;
import com.example.socapplication.service.AuthLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;
    private final JwtUtil jwtUtil;
    private final AuthLogService authLogService;

    public AuthController(AuthenticationManager authenticationManager,
                          AppUserService userDetailsService,
                          JwtUtil jwtUtil,
                          AuthLogService authLogService) {
        this.authenticationManager = authenticationManager;
        this.appUserService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authLogService = authLogService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request,
                                                     HttpServletRequest httpRequest) {
        String ip = getClientIp(httpRequest);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            UserDetails userDetails = appUserService.loadUserByUsername(request.email());
            String token = jwtUtil.generateToken(userDetails);

            log.info("Inloggning lyckades - email: {}, ip: {}", request.email(), ip);
            authLogService.logLogin(new CreateAuthLog(request.email(), ip, true, null, OffsetDateTime.now()));

            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            log.warn("Inloggning misslyckades - email: {}, ip: {}", request.email(), ip);
            authLogService.logLogin(new CreateAuthLog(request.email(), ip, false, "BAD_CREDENTIALS", null));
            throw e;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseAppUser> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(appUserService.register(request));
    }

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}