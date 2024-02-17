package com.inerxia.expensemateapi.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FilterSolicitudesRequest {
    private Integer idUsuario;

    private String nombreLista;

    @JsonIgnore
    private String estadoLista;

    @JsonIgnore
    private String estadoIntegrante;

    @JsonIgnore
    private boolean esCreador;
}
