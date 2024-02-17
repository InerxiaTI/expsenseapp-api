package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.dtos.responses.ConsultaDetalleCierreResponse;
import com.inerxia.expensemateapi.entities.DetalleCierre;
import com.inerxia.expensemateapi.exceptions.DataNotFoundException;
import com.inerxia.expensemateapi.repositories.DetalleCierreRepository;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DetalleCierreService {
    private final DetalleCierreRepository repository;

    public DetalleCierreService(DetalleCierreRepository repository) {
        this.repository = repository;
    }

    public DetalleCierre save(DetalleCierre detalleCierre) {
        detalleCierre.setCreatedDate(LocalDateTime.now());
        detalleCierre.setLastUpdate(LocalDateTime.now());
        return repository.save(detalleCierre);
    }

    public List<DetalleCierre> findAllByListaCompraId(Integer listaCompraId) {
        return repository.findAllByListaCompraId(listaCompraId);
    }

    public List<ConsultaDetalleCierreResponse> consultarDetalleCierre(Integer idListaCompras) {
        return repository.consultarDetalleCierre(idListaCompras);
    }

    public DetalleCierre findById(Integer idDetalleCierre) {
        return repository.findById(idDetalleCierre).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.CLOSURE_DETAIL_NOT_FOUND_EXCEPTION));
    }

    public void validateDetalleCierre(Integer idDetalleCierre) {
        repository.findById(idDetalleCierre).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.CLOSURE_DETAIL_NOT_FOUND_EXCEPTION));
    }

    public DetalleCierre update(DetalleCierre detalleCierre) {
        CustomUtilService.ValidateRequired(detalleCierre.getId());
        validateDetalleCierre(detalleCierre.getId());
        detalleCierre.setLastUpdate(LocalDateTime.now());
        return repository.save(detalleCierre);
    }

    public void deleteByListaCompraId(Integer listaCompraId) {
        repository.deleteByListaCompraId(listaCompraId);
    }
}
