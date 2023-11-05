package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EditarCompraRequest {
    private Integer idCompra;
    private Integer idCategoria;
    private Integer idUsuarioCompra;
    private Integer idUsuarioRegistro;
    private LocalDate fechaCompra;
    private String descripcion;
    private Double valor;
}
