package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.dtos.requests.FilterConsultaIntegrantesRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaIntegrantesResponse;
import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
import com.inerxia.expensemateapi.exceptions.BusinessException;
import com.inerxia.expensemateapi.exceptions.DataNotFoundException;
import com.inerxia.expensemateapi.repositories.IntegranteListaCompraRepository;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_COLABORADORES;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    public IntegranteListaCompra save(IntegranteListaCompra integranteListaCompra) {
        integranteListaCompra.setCreatedDate(LocalDateTime.now());
        integranteListaCompra.setLastUpdate(LocalDateTime.now());
        return repository.save(integranteListaCompra);
    }

    public IntegranteListaCompra update(IntegranteListaCompra integranteListaCompra) {
        CustomUtilService.ValidateRequired(integranteListaCompra.getId());
        validateIntegranteListaCompra(integranteListaCompra.getId());
        integranteListaCompra.setLastUpdate(LocalDateTime.now());
        return repository.save(integranteListaCompra);
    }

    public void validateIntegranteListaCompra(Integer idIntegrante) {
        repository.findById(idIntegrante).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.COLLABORATOR_NOT_FOUND_EXCEPTION));
    }

    public IntegranteListaCompra agregarColaborador(IntegranteListaCompra integranteListaCompra) {
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
        return save(integranteListaCompra);
    }

    public List<IntegranteListaCompra> findAllByListaCompraId(Integer listaCompraId) {
        return repository.findAllByListaCompraId(listaCompraId);
    }

    public IntegranteListaCompra findByListaCompraIdAndUsuarioId(Integer listaCompraId, Integer usuarioId) {
        return repository.findByListaCompraIdAndUsuarioId(listaCompraId, usuarioId).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.COLLABORATOR_NOT_FOUND_EXCEPTION));
    }

    public List<ConsultaIntegrantesResponse> consultarIntegrantesWithTotalCompras(FilterConsultaIntegrantesRequest filtro) {
        return repository.consultarIntegrantesWithTotalCompras(filtro);
    }

    public List<IntegranteListaCompra> consultarIntegrantesFilter(FilterConsultaIntegrantesRequest filtro) {
        return repository.consultarIntegrantesFilter(filtro);
    }

    public Double sumarPorcentajesIntegrantes(List<IntegranteListaCompra> integrantes) {
        return integrantes.stream()
                .mapToDouble(integrante -> Optional.ofNullable(integrante.getPorcentaje())
                        .orElse(0.0))
                .sum();
    }
}
