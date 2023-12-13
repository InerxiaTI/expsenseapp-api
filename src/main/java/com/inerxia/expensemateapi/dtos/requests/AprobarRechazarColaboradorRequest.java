package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class AprobarRechazarColaboradorRequest {
    private Integer idUsuarioCreador;
    private Integer idUsuarioColaborador;
    private Integer idListaCompras;
    private Boolean aprobar;
}
