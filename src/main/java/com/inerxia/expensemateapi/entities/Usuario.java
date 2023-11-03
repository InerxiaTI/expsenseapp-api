package com.inerxia.expensemateapi.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "usuarios")
@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "nombres", nullable = false, unique = true)
    private String nombres;

    @Column(name = "apellidos", nullable = false, unique = true)
    private String apellidos;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "correo")
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;
}
