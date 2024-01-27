package com.inerxia.expensemateapi.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "detalles_cierres")
@Entity
@Data
public class DetalleCierre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "lista_compra_fk", nullable = false)
    private Integer listaCompraId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lista_compra_fk", insertable = false, updatable = false)
    private ListaCompra listaCompra;

    @NotNull
    @Column(name = "usuario_deudor_fk", nullable = false)
    private Integer usuarioDeudorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_deudor_fk", insertable = false, updatable = false)
    private Usuario usuarioDeudor;

    @NotNull
    @Column(name = "usuario_acreedor_fk", nullable = false)
    private Integer usuarioAcreedorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_acreedor_fk", insertable = false, updatable = false)
    private Usuario usuarioAcreedor;

    @NotNull
    @Column(name = "total_deuda", nullable = false)
    private Double totalDeuda;

    @NotNull
    @Column(name = "aprobado", nullable = false)
    private Boolean aprobado;

    @Column(name = "fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
