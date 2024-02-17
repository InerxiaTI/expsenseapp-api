package com.inerxia.expensemateapi.dtos.responses;

import lombok.Data;

@Data
public class IndicadoresResponse {
    private Integer cantidadListasPendientes;
    private Double totalDineroGastado;
    private Double totalDeuda;

}
