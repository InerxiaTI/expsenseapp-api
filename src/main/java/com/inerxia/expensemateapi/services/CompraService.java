package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.dtos.requests.FiltroComprasRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaComprasResponse;
import com.inerxia.expensemateapi.repositories.CompraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CompraService {
    private final CompraRepository repository;

    public CompraService(CompraRepository repository) {
        this.repository = repository;
    }

    public Page<ConsultaComprasResponse> consultarComprasConFiltro(FiltroComprasRequest filtro, Pageable pageable){
        return repository.consultarComprasConFiltro(filtro, pageable);
    }
}
