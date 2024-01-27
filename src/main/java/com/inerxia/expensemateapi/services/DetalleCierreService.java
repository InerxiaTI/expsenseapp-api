package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.entities.DetalleCierre;
import com.inerxia.expensemateapi.repositories.DetalleCierreRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class DetalleCierreService {
    private final DetalleCierreRepository repository;

    public DetalleCierreService(DetalleCierreRepository repository) {
        this.repository = repository;
    }

    public DetalleCierre save(DetalleCierre detalleCierre){
        detalleCierre.setCreatedDate(LocalDateTime.now());
        detalleCierre.setLastUpdate(LocalDateTime.now());
        return repository.save(detalleCierre);
    }
}
