package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class CambiarEstadoDetalleCierreRequest {
    private Integer idDetalleCierre;
    private Boolean aprobado;
}
