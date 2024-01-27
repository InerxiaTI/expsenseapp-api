package com.inerxia.expensemateapi.repositories;

import com.inerxia.expensemateapi.dtos.responses.ConsultaDetalleCierreResponse;
import com.inerxia.expensemateapi.entities.DetalleCierre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetalleCierreRepository extends JpaRepository<DetalleCierre, Integer> {

    List<DetalleCierre> findAllByListaCompraId(Integer listaCompraId);

    @Query(value = "SELECT new com.inerxia.expensemateapi.dtos.responses.ConsultaDetalleCierreResponse(" +
            "dc.id, dc.listaCompraId, dc.usuarioDeudorId, ud.nombres, ud.apellidos, dc.usuarioAcreedorId, " +
            "ua.nombres, ua.apellidos, dc.totalDeuda, dc.aprobado, dc.fechaAprobacion, lc.fechaCierre) " +
            "FROM DetalleCierre dc " +
            "INNER JOIN Usuario ua ON ua.id = dc.usuarioAcreedorId " +
            "INNER JOIN Usuario ud ON ud.id = dc.usuarioDeudorId " +
            "INNER JOIN ListaCompra lc ON lc.id = dc.listaCompraId " +
            "WHERE (dc.listaCompraId = :idListaCompras) ")
    List<ConsultaDetalleCierreResponse> consultarDetalleCierre(@Param("idListaCompras") Integer idListaCompras);

}
