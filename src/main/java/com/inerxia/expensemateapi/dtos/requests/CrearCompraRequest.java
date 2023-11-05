package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearCompraRequest {
    private Integer idListaCompras;
    private Integer idCategoria;
    private Integer idUsuarioCompra;
    private Integer idUsuarioRegistro;
    private LocalDate fechaCompra;
    private String descripcion;
    private Double valor;
}
