package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.repositories.ListaCompraRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ListaCompraService {
    private final ListaCompraRepository repository;

    public ListaCompraService(ListaCompraRepository repository) {
        this.repository = repository;
    }

}
