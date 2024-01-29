package com.inerxia.expensemateapi.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "listas_compras")
@Entity
@Data
public class ListaCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_inicializacion")
    private LocalDateTime fechaInicializacion;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "fecha_finalizado")
    private LocalDateTime fechaFinalizado;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "total_compras", nullable = false)
    private Double totalCompras;

    @NotNull
    @Column(name = "usuario_creador_fk", nullable = false)
    private Integer usuarioCreadorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_creador_fk", insertable = false, updatable = false)
    private Usuario usuarioCreador;

    @Column(name = "codigo_generado")
    private String codigoGenerado;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
