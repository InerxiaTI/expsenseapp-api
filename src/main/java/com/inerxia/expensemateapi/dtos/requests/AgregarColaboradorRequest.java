package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class AgregarColaboradorRequest {
    private String codigoGenerado;
    private Integer idUsuarioColaborador;
}
