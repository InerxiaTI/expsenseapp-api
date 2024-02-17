package com.inerxia.expensemateapi.repositories;

import com.inerxia.expensemateapi.dtos.ListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.FilterIndicadoresRequest;
import com.inerxia.expensemateapi.dtos.requests.FilterListasComprasRequest;
import com.inerxia.expensemateapi.dtos.requests.FilterSolicitudesRequest;
import com.inerxia.expensemateapi.entities.ListaCompra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ListaCompraRepository extends JpaRepository<ListaCompra, Integer> {

    @Query(value = "SELECT new com.inerxia.expensemateapi.dtos.ListaCompraDto(" +
            "lc.id, lc.nombre, lc.fechaCreacion, lc.estado, lc.fechaFinalizado, lc.totalCompras, lc.usuarioCreadorId, lc.codigoGenerado) " +
            "FROM ListaCompra lc " +
            "INNER JOIN IntegranteListaCompra ilc ON ilc.listaCompraId = lc.id " +
            "WHERE (ilc.usuarioId = :#{#filtro.usuario}) " +
            "AND (ilc.estado = :#{#estadoColaborador}) " +
            "AND (:#{#filtro.estado} IS NULL OR lc.estado = :#{#filtro.estado}) " +
            "AND (:#{#filtro.nombre} IS NULL OR LOWER(lc.nombre) LIKE LOWER(CONCAT('%', CAST(:#{#filtro.nombre} AS string),'%'))) ")
    Page<ListaCompraDto> consultarListaCompras(@Param("filtro") FilterListasComprasRequest filtro,
                                               String estadoColaborador,
                                               Pageable pageable);

    Optional<ListaCompra> findByCodigoGenerado(String codigoGenerado);

    @Query(value = "SELECT new com.inerxia.expensemateapi.dtos.ListaCompraDto(" +
            "lc.id, lc.nombre, lc.fechaCreacion, lc.estado, lc.fechaFinalizado, lc.totalCompras, lc.usuarioCreadorId, lc.codigoGenerado) " +
            "FROM ListaCompra lc " +
            "INNER JOIN IntegranteListaCompra ilc ON ilc.listaCompraId = lc.id " +
            "WHERE (lc.estado = :#{#filtro.estadoLista}) " +
            "AND (ilc.usuarioId = :#{#filtro.idUsuario}) " +
            "AND (ilc.estado = :#{#filtro.estadoIntegrante}) " +
            "AND (ilc.esCreador = :#{#filtro.esCreador}) " +
            "AND (:#{#filtro.nombreLista} IS NULL OR LOWER(lc.nombre) LIKE LOWER(CONCAT('%', CAST(:#{#filtro.nombreLista} AS string),'%'))) ")
    Page<ListaCompraDto> consultarListasSolicitadas(@Param("filtro") FilterSolicitudesRequest filtro,
                                                    Pageable pageable);

    @Query(value = "SELECT COUNT(*) " +
            "FROM ListaCompra lc " +
            "INNER JOIN IntegranteListaCompra ilc ON ilc.listaCompraId = lc.id " +
            "WHERE (ilc.usuarioId = :#{#filtro.idUsuario}) " +
            "AND (ilc.estado = :#{#filtro.estadoIntegrante}) " +
            "AND (lc.estado IN (:#{#filtro.estadosLista})) ")
    Integer cantidadListasPendientes(@Param("filtro") FilterIndicadoresRequest filtro);

    @Query(value = "SELECT SUM(dc.totalDeuda) " +
            "FROM ListaCompra lc " +
            "INNER JOIN IntegranteListaCompra ilc ON ilc.listaCompraId = lc.id " +
            "INNER JOIN DetalleCierre dc ON dc.listaCompraId = lc.id " +
            "WHERE (ilc.usuarioId = :#{#filtro.idUsuario}) " +
            "AND (ilc.estado = :#{#filtro.estadoIntegrante}) " +
            "AND (lc.estado = :#{#filtro.estadoLista}) " +
            "AND (dc.usuarioDeudorId = :#{#filtro.idUsuario}) ")
    Double consultarTotalDeuda(@Param("filtro") FilterIndicadoresRequest filtro);
}
