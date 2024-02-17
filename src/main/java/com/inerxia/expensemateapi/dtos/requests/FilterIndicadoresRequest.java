package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

import java.util.List;

@Data
public class FilterIndicadoresRequest {
    private Integer idUsuario;
    private List<String> estadosLista;
    private String estadoLista;
    private String estadoIntegrante;
}
