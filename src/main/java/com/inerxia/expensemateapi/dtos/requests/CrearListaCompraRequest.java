package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class CrearListaCompraRequest {
    private String nombre;
    private Integer usuarioCreador;
}
