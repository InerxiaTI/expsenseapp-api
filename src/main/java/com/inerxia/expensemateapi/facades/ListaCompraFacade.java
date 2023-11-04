package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.mappers.ListaCompraMapper;
import com.inerxia.expensemateapi.services.ListaCompraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ListaCompraFacade {
    private final Logger log = LoggerFactory.getLogger(ListaCompraFacade.class);
    private final ListaCompraMapper listaCompraMapper;
    private final ListaCompraService listaCompraService;


    public ListaCompraFacade(ListaCompraMapper listaCompraMapper, ListaCompraService listaCompraService) {
        this.listaCompraMapper = listaCompraMapper;
        this.listaCompraService = listaCompraService;
    }
}
