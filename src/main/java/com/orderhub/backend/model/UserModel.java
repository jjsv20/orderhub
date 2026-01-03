package com.orderhub.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //columna for username
    @Column(name = "username", nullable = false, length = 100)
    private String username;
    
    //columna for email
    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    //columna for password
    @Column(name = "password", nullable = false)
    private String password;

    //@OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    //private BusinessModel business;

    //columna for role
    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role rol;

    public enum Role {
        OWNER,
        STAFF,
        CUSTOMER,
        SUPERADMIN
    }

    //columna for fecha de creacion
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

}
