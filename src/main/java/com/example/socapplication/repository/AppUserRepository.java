package com.example.socapplication.repository;

import com.example.socapplication.model.entity.AppUser;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends ListCrudRepository<AppUser, Long> {
    Optional<AppUser> findByEmailHash(String emailHash);
    List<AppUser> findByRole_Name(String roleName);
}