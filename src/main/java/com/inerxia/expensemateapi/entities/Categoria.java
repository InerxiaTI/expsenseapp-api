package com.inerxia.expensemateapi.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "categorias")
@Entity
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "usuario_creador_fk", nullable = false)
    private Integer usuarioCreadorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_creador_fk", insertable = false, updatable = false)
    private Usuario usuarioCreador;

}
