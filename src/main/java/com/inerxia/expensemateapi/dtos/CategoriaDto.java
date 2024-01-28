package com.inerxia.expensemateapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CategoriaDto {
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private Boolean esPrivada;

    @NotNull
    private Integer usuarioCreadorId;
    private UsuarioDto usuarioCreador;

    @NotNull
    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;
}
