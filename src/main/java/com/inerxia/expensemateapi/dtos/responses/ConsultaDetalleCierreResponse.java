package com.inerxia.expensemateapi.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaDetalleCierreResponse {
    private Integer id;
    private Integer idListaCompra;
    private Integer usuarioDeudorId;
    private String nombresDeudor;
    private String apellidosDeudor;
    private Integer usuarioAcreedorId;
    private String nombresAcreedor;
    private String apellidosAcreedor;
    private Double totalDeuda;
    private Boolean aprobado;
    private LocalDateTime fechaAprobacion;
    private LocalDateTime fechaCierre;

    public ConsultaDetalleCierreResponse(Integer id, Integer idListaCompra, Integer usuarioDeudorId, String nombresDeudor, String apellidosDeudor, Integer usuarioAcreedorId, String nombresAcreedor, String apellidosAcreedor, Double totalDeuda, Boolean aprobado, LocalDateTime fechaAprobacion, LocalDateTime fechaCierre) {
        this.id = id;
        this.idListaCompra = idListaCompra;
        this.usuarioDeudorId = usuarioDeudorId;
        this.nombresDeudor = nombresDeudor;
        this.apellidosDeudor = apellidosDeudor;
        this.usuarioAcreedorId = usuarioAcreedorId;
        this.nombresAcreedor = nombresAcreedor;
        this.apellidosAcreedor = apellidosAcreedor;
        this.totalDeuda = totalDeuda;
        this.aprobado = aprobado;
        this.fechaAprobacion = fechaAprobacion;
        this.fechaCierre = fechaCierre;
    }
}
