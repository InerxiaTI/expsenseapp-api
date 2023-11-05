package com.inerxia.expensemateapi.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "compras")
@Entity
@Data
public class Compra {

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
    @Column(name = "categoria_fk", nullable = false)
    private Integer categoriaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_fk", insertable = false, updatable = false)
    private Categoria categoria;

    @NotNull
    @Column(name = "usuario_compra_fk", nullable = false)
    private Integer usuarioCompraId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_compra_fk", insertable = false, updatable = false)
    private Usuario usuarioCompra;

    @NotNull
    @Column(name = "usuario_registro_fk", nullable = false)
    private Integer usuarioRegistroId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_registro_fk", insertable = false, updatable = false)
    private Usuario usuarioRegistro;

    @NotNull
    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime fechaCompra;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "valor")
    private Double valor;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
}
