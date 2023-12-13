package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
import com.inerxia.expensemateapi.exceptions.BusinessException;
import com.inerxia.expensemateapi.repositories.IntegranteListaCompraRepository;
import com.inerxia.expensemateapi.utils.MessageResponse;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_COLABORADORES;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class IntegranteListaCompraService {
    private final IntegranteListaCompraRepository repository;

    public IntegranteListaCompraService(IntegranteListaCompraRepository repository) {
        this.repository = repository;
    }

    public IntegranteListaCompra save(IntegranteListaCompra integranteListaCompra){
        return repository.save(integranteListaCompra);
    }

    public IntegranteListaCompra agregarColaborador(IntegranteListaCompra integranteListaCompra){
        List<IntegranteListaCompra> integrantes = findAllByListaCompraId(integranteListaCompra.getListaCompraId());

        Optional<IntegranteListaCompra> integranteFound = integrantes.stream()
                .filter(i -> Objects.equals(i.getUsuarioId(), integranteListaCompra.getUsuarioId()))
                .findFirst();

        if (integranteFound.isPresent()) {
            if (Objects.equals(integranteFound.get().getEstado(), ESTADOS_COLABORADORES.RECHAZADO.name())) {
                throw new BusinessException(MessageResponse.PARTNER_REQUEST_REJECTED);
            }
            throw new BusinessException(MessageResponse.DUPLICATE_USER_ON_PURCHASE_LIST);
        }
        return repository.save(integranteListaCompra);
    }

    public List<IntegranteListaCompra> findAllByListaCompraId(Integer listaCompraId){
        return repository.findAllByListaCompraId(listaCompraId);
    }
}
