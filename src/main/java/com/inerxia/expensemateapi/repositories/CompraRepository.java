package com.inerxia.expensemateapi.repositories;

import com.inerxia.expensemateapi.dtos.requests.FiltroComprasRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaComprasResponse;
import com.inerxia.expensemateapi.entities.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Integer> {

    @Query(value = "SELECT new com.inerxia.expensemateapi.dtos.responses.ConsultaComprasResponse(" +
            "c.id, c.descripcion, c.valor, ca.nombre, uc.nombres, uc.apellidos, ur.nombres, ur.apellidos," +
            "c.listaCompraId, c.categoriaId, c.usuarioCompraId, c.usuarioRegistroId, c.fechaCompra, " +
            "c.fechaCreacion) " +
            "FROM Compra c " +
            "INNER JOIN Categoria ca ON ca.id = c.categoriaId " +
            "INNER JOIN Usuario uc ON uc.id = c.usuarioCompraId " +
            "INNER JOIN Usuario ur ON ur.id = c.usuarioRegistroId " +
            "WHERE (c.listaCompraId = :#{#filtro.idListaCompras}) " +
            "AND (c.usuarioCompraId = :#{#filtro.idUsuarioCompra}) " +
            "AND (:#{#filtro.categoria} IS NULL OR LOWER(ca.nombre) LIKE LOWER(CONCAT('%', CAST(:#{#filtro.categoria} AS string),'%'))) " +
            "AND (:#{#filtro.descripcion} IS NULL OR LOWER(c.descripcion) LIKE LOWER(CONCAT('%', CAST(:#{#filtro.descripcion} AS string),'%'))) " +
            "ORDER BY c.fechaCompra DESC")
    Page<ConsultaComprasResponse> consultarComprasConFiltro(@Param("filtro") FiltroComprasRequest filtro,
                                                            Pageable pageable);

    @Query(value = "SELECT c " +
            "FROM Compra c " +
            "WHERE (c.listaCompraId = :#{#idListaCompras}) ")
    List<Compra> consultarComprasByListaCompra(Integer idListaCompras);

    @Query(value = "SELECT c " +
            "FROM Compra c " +
            "WHERE (c.categoriaId = :#{#idCategoria}) ")
    List<Compra> consultarComprasByCategoria(Integer idCategoria);
}
