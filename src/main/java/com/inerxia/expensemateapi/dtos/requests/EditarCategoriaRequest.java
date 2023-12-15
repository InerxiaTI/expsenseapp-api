package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class EditarCategoriaRequest {
    private Integer idCategoria;
    private String nombre;
    private Boolean esPrivada;
}
