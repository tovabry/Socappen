package com.example.socapplication.service;



import com.example.socapplication.model.dto.appUserDto.ResponseAppUser;
import com.example.socapplication.repository.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class AppUserService {
public final static String ACCESS_ADMIN = "Admin";
public final static String ACCESS_USER = "User";

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ResponseAppUser> findAllUsers() {
        return appUserRepository.findAll()
                .stream()
                .map(appUser -> new ResponseAppUser(
                        appUser.getId(),
                        appUser.getEmail(),
                        appUser.getStatus(),
                        appUser.getRole(),
                        appUser.getOnline()
                ))
                .toList();
    }

}
