package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
import com.inerxia.expensemateapi.repositories.IntegranteListaCompraRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class IntegranteListaCompraService {
    private final IntegranteListaCompraRepository repository;

    public IntegranteListaCompraService(IntegranteListaCompraRepository repository) {
        this.repository = repository;
    }

    public IntegranteListaCompra save(IntegranteListaCompra integranteListaCompra){
        return repository.save(integranteListaCompra);
    }
}
