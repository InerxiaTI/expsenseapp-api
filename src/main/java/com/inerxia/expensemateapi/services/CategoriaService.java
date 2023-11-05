package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.entities.Categoria;
import com.inerxia.expensemateapi.exceptions.DataNotFoundException;
import com.inerxia.expensemateapi.repositories.CategoriaRepository;
import com.inerxia.expensemateapi.utils.MessageResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
