package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class FiltroCategoriaRequest {
    private Integer idUsuarioCreador;
    private String nombre;
    private Boolean esPrivada;
}
