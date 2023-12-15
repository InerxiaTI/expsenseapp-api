package com.inerxia.expensemateapi.repositories;

import com.inerxia.expensemateapi.dtos.requests.FiltroCategoriaRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaCategoriaResponse;
import com.inerxia.expensemateapi.entities.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Query(value = "SELECT new com.inerxia.expensemateapi.dtos.responses.ConsultaCategoriaResponse(" +
            "c.id, c.nombre, c.esPrivada, c.usuarioCreadorId, c.createdDate, c.lastUpdate) " +
            "FROM Categoria c " +
            "WHERE (c.usuarioCreadorId = :#{#filtro.idUsuarioCreador}) " +
            "AND (:#{#filtro.nombre} IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', CAST(:#{#filtro.nombre} AS string),'%'))) " +
            "AND (:#{#filtro.esPrivada} IS NULL OR c.esPrivada = :#{#filtro.esPrivada}) " +
            "ORDER BY c.nombre ASC ")
    Page<ConsultaCategoriaResponse> consultarCategoriasConFiltro(@Param("filtro") FiltroCategoriaRequest filtro,
                                                                 Pageable pageable);
}
