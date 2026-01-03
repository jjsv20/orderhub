package com.orderhub.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderhub.backend.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    // Métodos para interactuar con la base de datos de usuarios
    boolean existsByUsername(String username);
    Optional<UserModel> findByUsername(String username);
    boolean existsByEmail(String email);
    Optional<UserModel> findByEmail(String email);
}