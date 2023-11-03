package com.inerxia.expensemateapi.repositories;

import com.inerxia.expensemateapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
