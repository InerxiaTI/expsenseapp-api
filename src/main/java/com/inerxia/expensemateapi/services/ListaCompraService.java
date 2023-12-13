package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.dtos.ListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.FilterListasComprasRequest;
import com.inerxia.expensemateapi.entities.ListaCompra;
import com.inerxia.expensemateapi.exceptions.DataNotFoundException;
import com.inerxia.expensemateapi.repositories.ListaCompraRepository;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_COLABORADORES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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

    public void validateListaCompra(Integer idListaCompras) {
        repository.findById(idListaCompras).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.LIST_NOT_FOUND_EXCEPTION));
    }

    public ListaCompra findById(Integer idListaCompras) {
        return repository.findById(idListaCompras).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.LIST_NOT_FOUND_EXCEPTION));
    }

    public ListaCompra findByCodigoGenerado(String codigoGenerado) {
        return repository.findByCodigoGenerado(codigoGenerado).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.LIST_NOT_FOUND_EXCEPTION));
    }

    public ListaCompra update(ListaCompra listaCompra){
        CustomUtilService.ValidateRequired(listaCompra.getId());
        validateListaCompra(listaCompra.getId());
        return repository.save(listaCompra);
    }

    public Page<ListaCompraDto> consultarListaCompras(@Param("filtro") FilterListasComprasRequest filtro, Pageable pageable){
        String estadoColaborador = ESTADOS_COLABORADORES.APROBADO.name();
        return repository.consultarListaCompras(filtro, estadoColaborador, pageable);
    }

    public ListaCompra save(ListaCompra listaCompra, String randomCode){
        ListaCompra listaCompraSaved = repository.save(listaCompra);

        listaCompraSaved.setCodigoGenerado(listaCompraSaved.getId().toString().concat(randomCode));
        return repository.save(listaCompraSaved);
    }
}
