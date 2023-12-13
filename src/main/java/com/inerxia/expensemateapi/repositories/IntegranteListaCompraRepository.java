package com.inerxia.expensemateapi.repositories;

import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntegranteListaCompraRepository extends JpaRepository<IntegranteListaCompra, Integer> {

    List<IntegranteListaCompra> findAllByListaCompraId(Integer listaCompraId);
}
