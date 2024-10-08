package com.inerxia.expensemateapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class IntegranteListaCompraDto {
    private Integer id;

    @NotNull
    private Integer listaCompraId;
    private ListaCompraDto listaCompra;

    @NotNull
    private Integer usuarioId;
    private UsuarioDto usuario;
    private Double porcentaje;

    @NotNull
    private String  estado;

    @NotNull
    private Boolean esCreador;

    @NotNull
    private LocalDateTime createdDate;

    private LocalDateTime lastUpdate;
}
