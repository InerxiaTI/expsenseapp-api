package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.IntegranteListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.AgregarColaboradorRequest;
import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
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
import java.util.Objects;

@Service
@Transactional
public class IntegranteListaCompraFacade {
    private final Logger log = LoggerFactory.getLogger(IntegranteListaCompraFacade.class);
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
}
