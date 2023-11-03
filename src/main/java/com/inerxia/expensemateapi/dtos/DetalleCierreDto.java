package com.inerxia.expensemateapi.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class DetalleCierreDto {
    private Integer id;

    @NotNull
    private Integer listaCompraId;
    private ListaCompraDto listaCompra;

    @NotNull
    private Integer usuarioDeudorId;
    private UsuarioDto usuarioDeudor;

    @NotNull
    private Integer usuarioAcreedorId;
    private UsuarioDto usuarioAcreedor;

    @NotNull
    private Double totalDeuda;
    private Boolean aprobado;
    private LocalDateTime fechaAprobacion;
}
