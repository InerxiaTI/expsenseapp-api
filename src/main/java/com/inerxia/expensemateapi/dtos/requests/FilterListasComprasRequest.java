package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class FilterListasComprasRequest {
    private String estado;
    private String nombre;
    private Integer usuario;
}
