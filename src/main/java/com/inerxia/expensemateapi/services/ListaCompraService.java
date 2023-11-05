package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.entities.ListaCompra;
import com.inerxia.expensemateapi.exceptions.DataNotFoundException;
import com.inerxia.expensemateapi.repositories.ListaCompraRepository;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ListaCompraService {
    private final ListaCompraRepository repository;

    public ListaCompraService(ListaCompraRepository repository) {
        this.repository = repository;
    }

    public boolean listaCompraExists(Integer idListaCompras){
        CustomUtilService.ValidateRequired(idListaCompras);
        var listaCompra = repository.findById(idListaCompras);
        return listaCompra.isPresent();
    }

    public ListaCompra findById(Integer idListaCompras) {
        return repository.findById(idListaCompras).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.LIST_NOT_FOUND_EXCEPTION));
    }
}
