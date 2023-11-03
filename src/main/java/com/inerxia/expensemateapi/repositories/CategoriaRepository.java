package com.inerxia.expensemateapi.repositories;

import com.inerxia.expensemateapi.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
