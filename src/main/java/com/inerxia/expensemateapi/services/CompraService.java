package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.dtos.requests.FiltroComprasRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaComprasResponse;
import com.inerxia.expensemateapi.entities.Compra;
import com.inerxia.expensemateapi.exceptions.DataNotFoundException;
import com.inerxia.expensemateapi.repositories.CompraRepository;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public Compra save(Compra compra){
        return repository.save(compra);
    }

    public List<Compra> consultarComprasByListaCompra(Integer idListaCompra){
        return repository.consultarComprasByListaCompra(idListaCompra);
    }

    public Compra findById(Integer idCompra) {
        return repository.findById(idCompra).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.PURCHASE_NOT_FOUND_EXCEPTION));
    }

    public void validateCompra(Integer idCompra) {
        repository.findById(idCompra).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.PURCHASE_NOT_FOUND_EXCEPTION));
    }

    public Compra update(Compra compra){
        CustomUtilService.ValidateRequired(compra.getId());
        validateCompra(compra.getId());
        return repository.save(compra);
    }
}
