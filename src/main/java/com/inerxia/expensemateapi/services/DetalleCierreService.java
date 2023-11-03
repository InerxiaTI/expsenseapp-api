package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.repositories.DetalleCierreRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DetalleCierreService {
    private final DetalleCierreRepository repository;

    public DetalleCierreService(DetalleCierreRepository repository) {
        this.repository = repository;
    }

}
