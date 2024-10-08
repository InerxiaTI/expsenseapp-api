package com.inerxia.expensemateapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CompraDto {
    private Integer id;

    @NotNull
    private Integer listaCompraId;
    private ListaCompraDto listaCompra;

    @NotNull
    private Integer categoriaId;
    private CategoriaDto categoria;

    @NotNull
    private Integer usuarioCompraId;
    private UsuarioDto usuarioCompra;

    @NotNull
    private Integer usuarioRegistroId;
    private UsuarioDto usuarioRegistro;

    private LocalDateTime fechaCompra;
    private String descripcion;
    private Double valor;

    @NotNull
    private LocalDateTime fechaCreacion;

    @NotNull
    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;
}
