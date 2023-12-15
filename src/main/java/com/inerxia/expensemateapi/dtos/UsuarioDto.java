package com.inerxia.expensemateapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UsuarioDto {
    private Integer id;

    @NotNull
    private String nombres;

    private String apellidos;
    private Boolean activo;
    private String correo;
    private String contrasena;

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime lastUpdate;
}
