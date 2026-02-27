package com.example.socapplication.repository;

import com.example.socapplication.model.entity.AppUser;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends ListCrudRepository<AppUser, Integer> {

}