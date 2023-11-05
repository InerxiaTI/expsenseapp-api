package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class FiltroComprasRequest {
    private String descripcion;
    private String categoria;
    private Integer idUsuarioCompra;
    private Integer idListaCompras;
}
