package com.inerxia.expensemateapi.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaComprasResponse {
    private Integer id;
    private String descripcion;
    private Double valor;
    private String nombreCategoria;
    private String nombresUsuarioCompra;
    private String apellidosUsuarioCompra;
    private String nombresUsuarioRegistro;
    private String apellidosUsuarioRegistro;
    private Integer idListaCompra;
    private Integer idCategoria;
    private Integer idUsuarioCompra;
    private Integer idUsuarioRegistro;
    private LocalDateTime fechaCompra;
    private LocalDateTime fechaCreacion;

    public ConsultaComprasResponse(Integer id, String descripcion, Double valor, String nombreCategoria, String nombresUsuarioCompra,
                                   String apellidosUsuarioCompra, String nombresUsuarioRegistro, String apellidosUsuarioRegistro,
                                   Integer idListaCompra, Integer idCategoria, Integer idUsuarioCompra, Integer idUsuarioRegistro,
                                   LocalDateTime fechaCompra, LocalDateTime fechaCreacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.valor = valor;
        this.nombreCategoria = nombreCategoria;
        this.nombresUsuarioCompra = nombresUsuarioCompra;
        this.apellidosUsuarioCompra = apellidosUsuarioCompra;
        this.nombresUsuarioRegistro = nombresUsuarioRegistro;
        this.apellidosUsuarioRegistro = apellidosUsuarioRegistro;
        this.idListaCompra = idListaCompra;
        this.idCategoria = idCategoria;
        this.idUsuarioCompra = idUsuarioCompra;
        this.idUsuarioRegistro = idUsuarioRegistro;
        this.fechaCompra = fechaCompra;
        this.fechaCreacion = fechaCreacion;
    }
}
