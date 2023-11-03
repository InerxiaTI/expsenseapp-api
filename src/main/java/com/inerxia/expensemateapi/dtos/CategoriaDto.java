package com.inerxia.expensemateapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoriaDto {
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private Integer usuarioCreadorId;
    private UsuarioDto usuarioCreador;
}
