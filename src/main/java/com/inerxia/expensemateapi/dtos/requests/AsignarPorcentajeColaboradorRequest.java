package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class AsignarPorcentajeColaboradorRequest {
    private Integer idUsuarioCreador;
    private Integer idUsuarioColaborador;
    private Integer idListaCompras;
    private Double porcentaje;
}
