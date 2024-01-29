package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class DeudaBidireccional {
    private Integer idUsuarioDeudor;
    private Integer idUsuarioAcreedor;
    private Double valorDeuda;
}
