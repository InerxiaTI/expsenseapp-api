package com.inerxia.expensemateapi.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaCategoriaResponse {
    private Integer id;
    private String nombre;
    private Boolean esPrivada;
    private Integer usuarioCreadorId;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdate;

    public ConsultaCategoriaResponse(Integer id, String nombre, Boolean esPrivada, Integer usuarioCreadorId, LocalDateTime createdDate, LocalDateTime lastUpdate) {
        this.id = id;
        this.nombre = nombre;
        this.esPrivada = esPrivada;
        this.usuarioCreadorId = usuarioCreadorId;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
    }
}
