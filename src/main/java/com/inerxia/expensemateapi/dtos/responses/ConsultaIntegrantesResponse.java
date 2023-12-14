package com.inerxia.expensemateapi.dtos.responses;

import lombok.Data;

@Data
public class ConsultaIntegrantesResponse {
    private Integer id;
    private Double porcentaje;
    private String nombres;
    private String apellidos;
    private String estado;
    private Integer idListaCompra;
    private Integer idUsuario;
    private Boolean esCreador;
    private Double totalCompras;

    public ConsultaIntegrantesResponse(Integer id, Double porcentaje, String nombres, String apellidos, String estado, Integer idListaCompra, Integer idUsuario, Boolean esCreador, Double totalCompras) {
        this.id = id;
        this.porcentaje = porcentaje;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.estado = estado;
        this.idListaCompra = idListaCompra;
        this.idUsuario = idUsuario;
        this.esCreador = esCreador;
        this.totalCompras = totalCompras;
    }
}
