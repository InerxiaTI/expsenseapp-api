package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.dtos.requests.FiltroCategoriaRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaCategoriaResponse;
import com.inerxia.expensemateapi.entities.Categoria;
import com.inerxia.expensemateapi.exceptions.DataNotFoundException;
import com.inerxia.expensemateapi.repositories.CategoriaRepository;
import com.inerxia.expensemateapi.utils.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public void validateCategoria(Integer idCategoria) {
        repository.findById(idCategoria).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.CATEGORY_NOT_FOUND_EXCEPTION));
    }

    public Categoria findById(Integer idCategoria) {
        return repository.findById(idCategoria).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.CATEGORY_NOT_FOUND_EXCEPTION));
    }

    public Page<ConsultaCategoriaResponse> consultarCategoriasConFiltro(@Param("filtro") FiltroCategoriaRequest filtro, Pageable pageable){
        return repository.consultarCategoriasConFiltro(filtro, pageable);
    }

    public List<Categoria> consultarCategoriasDelCreadorConFiltro(Integer usuarioCreadorId){
        return repository.consultarCategoriasDelCreadorConFiltro(usuarioCreadorId);
    }
}
