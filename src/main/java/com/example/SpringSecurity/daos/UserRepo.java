package com.example.SpringSecurity.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringSecurity.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String username);
}
