package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

import java.util.List;

@Data
public class FilterConsultaIntegrantesRequest {
    private Integer idListaCompras;
    private List<String> estados;
}
