package com.inerxia.expensemateapi.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "integrantes_listas_compras")
@Entity
@Data
public class IntegranteListaCompra {

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
    @Column(name = "usuario_fk", nullable = false)
    private Integer usuarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_fk", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "porcentaje")
    private Double porcentaje;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String  estado;

    @NotNull
    @Column(name = "es_creador", nullable = false)
    private Boolean esCreador;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
