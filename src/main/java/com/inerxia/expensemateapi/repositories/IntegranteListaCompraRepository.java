package com.inerxia.expensemateapi.repositories;

import com.inerxia.expensemateapi.dtos.requests.FilterConsultaIntegrantesRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaIntegrantesResponse;
import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IntegranteListaCompraRepository extends JpaRepository<IntegranteListaCompra, Integer> {

    List<IntegranteListaCompra> findAllByListaCompraId(Integer listaCompraId);

    Optional<IntegranteListaCompra> findByListaCompraIdAndUsuarioId(Integer listaCompraId, Integer usuarioId);

    @Query(value = "SELECT new com.inerxia.expensemateapi.dtos.responses.ConsultaIntegrantesResponse(" +
            "ilc.id, ilc.porcentaje, u.nombres, u.apellidos, ilc.estado, ilc.listaCompraId, ilc.usuarioId, ilc.esCreador, SUM(c.valor)) " +
            "FROM IntegranteListaCompra ilc " +
            "INNER JOIN Usuario u ON u.id = ilc.usuarioId " +
            "LEFT JOIN Compra c ON c.listaCompraId = ilc.listaCompraId AND ilc.usuarioId = c.usuarioCompraId " +
            "WHERE (ilc.listaCompraId = :#{#filtro.idListaCompras}) " +
            "AND (:#{#filtro.estados} is null or ilc.estado IN (:#{#filtro.estados})) " +
            "GROUP BY ilc.id, ilc.porcentaje, u.nombres, u.apellidos, ilc.estado, ilc.listaCompraId, ilc.usuarioId, ilc.esCreador")
    List<ConsultaIntegrantesResponse> consultarIntegrantesWithTotalCompras(@Param("filtro") FilterConsultaIntegrantesRequest filtro);

    @Query(value = "SELECT ilc " +
            "FROM IntegranteListaCompra ilc " +
            "WHERE (ilc.listaCompraId = :#{#filtro.idListaCompras}) " +
            "AND (:#{#filtro.estados} is null or ilc.estado IN (:#{#filtro.estados})) ")
    List<IntegranteListaCompra> consultarIntegrantesFilter(@Param("filtro") FilterConsultaIntegrantesRequest filtro);
}
