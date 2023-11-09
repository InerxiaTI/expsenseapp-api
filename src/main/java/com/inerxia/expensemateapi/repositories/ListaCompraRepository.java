package com.inerxia.expensemateapi.repositories;

import com.inerxia.expensemateapi.dtos.ListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.FilterListasComprasRequest;
import com.inerxia.expensemateapi.entities.ListaCompra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListaCompraRepository extends JpaRepository<ListaCompra, Integer> {

    @Query(value = "SELECT new com.inerxia.expensemateapi.dtos.ListaCompraDto(" +
            "lc.id, lc.nombre, lc.fechaCreacion, lc.estado, lc.fechaFinalizado, lc.totalCompras, lc.usuarioCreadorId, lc.codigoGenerado) " +
            "FROM ListaCompra lc " +
            "INNER JOIN IntegranteListaCompra ilc ON ilc.listaCompraId = lc.id " +
            "WHERE (ilc.usuarioId = :#{#filtro.usuario}) " +
            "AND (ilc.estado = :#{#estadoColaborador}) " +
            "AND (:#{#filtro.estado} IS NULL OR lc.estado = :#{#filtro.estado}) " +
            "AND (:#{#filtro.nombre} IS NULL OR LOWER(lc.nombre) LIKE LOWER(CONCAT('%', CAST(:#{#filtro.nombre} AS string),'%'))) " )
    Page<ListaCompraDto> consultarListaCompras(@Param("filtro") FilterListasComprasRequest filtro,
                                               String estadoColaborador,
                                               Pageable pageable);
}
