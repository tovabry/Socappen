package com.example.socapplication.controller;

import com.example.socapplication.model.dto.appUserDto.RegisterRequest;
import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.model.dto.authLogDto.CreateAuthLog;
import com.example.socapplication.model.dto.login.LoginRequest;
import com.example.socapplication.security.JwtUtil;
import com.example.socapplication.service.AppUserService;
import com.example.socapplication.service.AuthLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseCookie;


import java.time.Duration;
import java.time.OffsetDateTime;

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
    public ResponseEntity<Void> login(@RequestBody LoginRequest request,
                                      HttpServletRequest httpRequest,
                                      HttpServletResponse httpResponse) {
        String ip = getClientIp(httpRequest);
        Long userId = appUserService.findByEmail(request.email()).getId();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            UserDetails userDetails = appUserService.loadUserByUsername(request.email());
            String token = jwtUtil.generateToken(userDetails);

            log.info("Generated token length: {}", token.length());
            log.info("Generated token: {}", token);

            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .secure(false) //change to true in production
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .sameSite("Lax") //blocks cookies on cross-site requests
                    .domain("localhost")
                    .build();

            httpResponse.addHeader("Set-Cookie", cookie.toString());

            log.info("Inloggning lyckades - user id: {}, ip: {}", userId, ip);
            authLogService.logLogin(new CreateAuthLog(userId, ip, true, null, OffsetDateTime.now()));

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.warn("Inloggning misslyckades - user id: {}, ip: {}", userId, ip);
            authLogService.logLogin(new CreateAuthLog(userId, ip, false, "BAD_CREDENTIALS", null));
            throw e;
        }
    }

    //frontend must send CSRF token or logout will fail with 403
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseAppUser> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(appUserService.register(request));
    }

    private String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}