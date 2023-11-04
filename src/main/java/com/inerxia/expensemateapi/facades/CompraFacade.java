package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.mappers.CompraMapper;
import com.inerxia.expensemateapi.services.CompraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CompraFacade {
    private final Logger log = LoggerFactory.getLogger(CompraFacade.class);
    private final CompraMapper compraMapper;
    private final CompraService compraService;

    public CompraFacade(CompraMapper compraMapper, CompraService compraService) {
        this.compraMapper = compraMapper;
        this.compraService = compraService;
    }
}
