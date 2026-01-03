package com.orderhub.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tbl_businesses")
@NoArgsConstructor
@AllArgsConstructor
public class BusinessModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //columna for nombre del negocio
    @Column(name = "namebusiness", nullable = false, length = 100)
    private String namebusiness;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserModel owner;

    //columna for email del negocio
    @Column(name = "emailbusiness", nullable = true, length = 150)
    private String emailbusiness;

    //columna for direccion del negocio
    @Column(name = "addressbusiness", nullable = true, length = 200)
    private String addressbusiness;

    //columna for telefono del negocio
    @Column(name = "phonebusiness", nullable = true, length = 15)  
    private String phonebusiness;

    //columna for estado del negocio
    @Column(name = "statusbusiness", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status statusbusiness = Status.INCOMPLETE;

    public enum Status {
        INCOMPLETE,
        ACTIVE,
        SUSPENDED
    }

     //columna for fecha de creacion
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
