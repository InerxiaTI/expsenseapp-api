package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.IntegranteListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.AgregarColaboradorRequest;
import com.inerxia.expensemateapi.dtos.requests.AprobarRechazarColaboradorRequest;
import com.inerxia.expensemateapi.dtos.requests.AsignarPorcentajeColaboradorRequest;
import com.inerxia.expensemateapi.dtos.requests.FilterConsultaIntegrantesRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaIntegrantesResponse;
import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
import com.inerxia.expensemateapi.entities.ListaCompra;
import com.inerxia.expensemateapi.exceptions.BusinessException;
import com.inerxia.expensemateapi.exceptions.RequestErrorException;
import com.inerxia.expensemateapi.mappers.IntegranteListaCompraMapper;
import com.inerxia.expensemateapi.services.IntegranteListaCompraService;
import com.inerxia.expensemateapi.services.ListaCompraService;
import com.inerxia.expensemateapi.services.UsuarioService;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_COLABORADORES;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_LISTA_COMPRAS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class IntegranteListaCompraFacade {
    private final Logger log = LoggerFactory.getLogger(IntegranteListaCompraFacade.class);
    private final Integer MINIMUM_PERCENTAGE = 0;
    private final Integer MAXIMUM_PERCENTAGE = 100;
    private final IntegranteListaCompraMapper integranteListaCompraMapper;
    private final IntegranteListaCompraService integranteListaCompraService;
    private final ListaCompraService listaCompraService;
    private final UsuarioService usuarioService;

    public IntegranteListaCompraFacade(IntegranteListaCompraMapper integranteListaCompraMapper, IntegranteListaCompraService integranteListaCompraService, ListaCompraService listaCompraService, UsuarioService usuarioService) {
        this.integranteListaCompraMapper = integranteListaCompraMapper;
        this.integranteListaCompraService = integranteListaCompraService;
        this.listaCompraService = listaCompraService;
        this.usuarioService = usuarioService;
    }

    public IntegranteListaCompraDto agregarIntegranteColaborador(AgregarColaboradorRequest request) {
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getCodigoGenerado());
        CustomUtilService.ValidateRequired(request.getIdUsuarioColaborador());

        var listaCompra = listaCompraService.findByCodigoGenerado(request.getCodigoGenerado());

        if (!Objects.equals(listaCompra.getEstado(), ESTADOS_LISTA_COMPRAS.CONFIGURANDO.name())) {
            throw new RequestErrorException(MessageResponse.ADD_COLLABORATOR_NOT_ALLOWED);
        }

        usuarioService.validateUsuarioActivo(request.getIdUsuarioColaborador());

        if (Objects.equals(listaCompra.getUsuarioCreadorId(), request.getIdUsuarioColaborador())) {
            throw new BusinessException(MessageResponse.DUPLICATE_USER_ON_PURCHASE_LIST);
        }

        IntegranteListaCompra integranteListaCompra = new IntegranteListaCompra();
        integranteListaCompra.setListaCompraId(listaCompra.getId());
        integranteListaCompra.setUsuarioId(request.getIdUsuarioColaborador());
        integranteListaCompra.setEstado(ESTADOS_COLABORADORES.PENDIENTE.name());
        integranteListaCompra.setEsCreador(false);
        IntegranteListaCompra integranteSaved = integranteListaCompraService.agregarColaborador(integranteListaCompra);
        return integranteListaCompraMapper.toDto(integranteSaved);
    }

    public IntegranteListaCompraDto aprobarRechazarColaborador(AprobarRechazarColaboradorRequest request) {
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getIdUsuarioCreador());
        CustomUtilService.ValidateRequired(request.getIdUsuarioColaborador());
        CustomUtilService.ValidateRequired(request.getIdListaCompras());

        usuarioService.validateUsuarioActivo(request.getIdUsuarioCreador());
        usuarioService.validateUsuarioActivo(request.getIdUsuarioColaborador());

        ListaCompra listaCompra = listaCompraService.findById(request.getIdListaCompras());
        if (!Objects.equals(listaCompra.getUsuarioCreadorId(), request.getIdUsuarioCreador())) {
            throw new BusinessException(MessageResponse.NOT_ALLOWED_ENABLE);
        }

        if (!Objects.equals(listaCompra.getEstado(), ESTADOS_LISTA_COMPRAS.CONFIGURANDO.name())) {
            throw new RequestErrorException(MessageResponse.ENABLE_COLLABORATOR_NOT_ALLOWED);
        }

        if (Objects.equals(listaCompra.getUsuarioCreadorId(), request.getIdUsuarioColaborador())) {
            throw new BusinessException(MessageResponse.CHANGE_REQUEST_STATUS_TO_CREATOR_NOT_ALLOWED);
        }

        IntegranteListaCompra integranteFound = integranteListaCompraService.findByListaCompraIdAndUsuarioId(listaCompra.getId(), request.getIdUsuarioColaborador());

        if (request.getAprobar()) {
            integranteFound.setEstado(ESTADOS_COLABORADORES.APROBADO.name());
        } else {
            integranteFound.setEstado(ESTADOS_COLABORADORES.RECHAZADO.name());
        }
        integranteListaCompraService.update(integranteFound);
        return integranteListaCompraMapper.toDto(integranteFound);
    }

    public IntegranteListaCompraDto asignarPorcentajeColaborador(AsignarPorcentajeColaboradorRequest request) {
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getIdUsuarioColaborador());
        CustomUtilService.ValidateRequired(request.getIdListaCompras());
        CustomUtilService.ValidateRequired(request.getPorcentaje());

        usuarioService.validateUsuarioActivo(request.getIdUsuarioColaborador());

        ListaCompra listaCompra = listaCompraService.findById(request.getIdListaCompras());
        if (!Objects.equals(listaCompra.getEstado(), ESTADOS_LISTA_COMPRAS.CONFIGURANDO.name())) {
            throw new RequestErrorException(MessageResponse.CHANGE_PERCENTAGE_NOT_ALLOWED);
        }

        if (request.getPorcentaje() < MINIMUM_PERCENTAGE || request.getPorcentaje() > MAXIMUM_PERCENTAGE) {
            throw new BusinessException(MessageResponse.PERCENT_NOT_ALLOWED);
        }

        IntegranteListaCompra integranteFound = integranteListaCompraService.findByListaCompraIdAndUsuarioId(listaCompra.getId(), request.getIdUsuarioColaborador());

        if (Objects.equals(integranteFound.getEstado(), ESTADOS_COLABORADORES.RECHAZADO.name())) {
            throw new BusinessException(MessageResponse.PARTNER_REQUEST_REJECTED);
        }

        if (Objects.equals(integranteFound.getEstado(), ESTADOS_COLABORADORES.PENDIENTE.name())) {
            throw new BusinessException(MessageResponse.REQUEST_HAS_NOT_BEEN_APPROVED);
        }

        integranteFound.setPorcentaje(request.getPorcentaje());
        integranteListaCompraService.update(integranteFound);
        return integranteListaCompraMapper.toDto(integranteFound);
    }

    public List<ConsultaIntegrantesResponse> consultarIntegrantesWithTotalCompras(FilterConsultaIntegrantesRequest filter) {
        CustomUtilService.ValidateRequired(filter);
        CustomUtilService.ValidateRequired(filter.getIdListaCompras());

        listaCompraService.validateListaCompra(filter.getIdListaCompras());

        return integranteListaCompraService.consultarIntegrantesWithTotalCompras(filter);
    }

}