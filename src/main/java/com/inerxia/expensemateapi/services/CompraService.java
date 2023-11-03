package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.repositories.CompraRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CompraService {
    private final CompraRepository repository;

    public CompraService(CompraRepository repository) {
        this.repository = repository;
    }

}
