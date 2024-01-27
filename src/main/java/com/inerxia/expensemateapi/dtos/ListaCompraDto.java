package com.inerxia.expensemateapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ListaCompraDto {
    private Integer id;

    @NotNull
    private String nombre;

    @NotNull
    private LocalDateTime fechaCreacion;

    @NotNull
    private LocalDateTime fechaInicializacion;

    @NotNull
    private LocalDateTime fechaCierre;

    @NotNull
    private LocalDateTime fechaFinalizado;

    @NotNull
    private String estado;

    @NotNull
    private Double totalCompras;

    @NotNull
    private Integer usuarioCreadorId;
    private UsuarioDto usuarioCreador;

    @NotNull
    private String codigoGenerado;

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private LocalDateTime lastUpdate;

    public ListaCompraDto(Integer id, String nombre, LocalDateTime fechaCreacion, String estado, LocalDateTime fechaFinalizado,
                          Double totalCompras, Integer usuarioCreadorId, String codigoGenerado) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.fechaFinalizado = fechaFinalizado;
        this.totalCompras = totalCompras;
        this.usuarioCreadorId = usuarioCreadorId;
        this.codigoGenerado = codigoGenerado;
    }
}
