package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.ListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.CrearListaCompraRequest;
import com.inerxia.expensemateapi.dtos.requests.FilterListasComprasRequest;
import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
import com.inerxia.expensemateapi.entities.ListaCompra;
import com.inerxia.expensemateapi.mappers.ListaCompraMapper;
import com.inerxia.expensemateapi.services.IntegranteListaCompraService;
import com.inerxia.expensemateapi.services.ListaCompraService;
import com.inerxia.expensemateapi.services.UsuarioService;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.GenerateRandomCodeService;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_COLABORADORES;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_LISTA_COMPRAS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class ListaCompraFacade {
    private final Logger log = LoggerFactory.getLogger(ListaCompraFacade.class);
    private final ListaCompraMapper listaCompraMapper;
    private final ListaCompraService listaCompraService;
    private final UsuarioService usuarioService;
    private final IntegranteListaCompraService integranteListaCompraService;

    public ListaCompraFacade(ListaCompraMapper listaCompraMapper, ListaCompraService listaCompraService, UsuarioService usuarioService, IntegranteListaCompraService integranteListaCompraService) {
        this.listaCompraMapper = listaCompraMapper;
        this.listaCompraService = listaCompraService;
        this.usuarioService = usuarioService;
        this.integranteListaCompraService = integranteListaCompraService;
    }

    public Page<ListaCompraDto> consultarListaComprasConFiltroConPaginacion(FilterListasComprasRequest filtro, Pageable pageable){
        CustomUtilService.ValidateRequired(filtro);
        CustomUtilService.ValidateRequired(filtro.getUsuario());

        usuarioService.validateUsuario(filtro.getUsuario());
        usuarioService.validateUsuarioActivo(filtro.getUsuario());

        return listaCompraService.consultarListaCompras(filtro, pageable);
    }

    public ListaCompraDto crearListaCompra(CrearListaCompraRequest request) {
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getNombre());
        CustomUtilService.ValidateRequired(request.getUsuarioCreador());

        usuarioService.validateUsuario(request.getUsuarioCreador());

        var listaCompra = new ListaCompra();
        listaCompra.setNombre(request.getNombre());
        listaCompra.setUsuarioCreadorId(request.getUsuarioCreador());
        listaCompra.setFechaCreacion(LocalDateTime.now());
        listaCompra.setEstado(ESTADOS_LISTA_COMPRAS.CONFIGURANDO.name());
        listaCompra.setTotalCompras(0.0);

        String randomCode = GenerateRandomCodeService.generateRandomCode();
        ListaCompra listaCompraSaved = listaCompraService.save(listaCompra, randomCode);

        var integranteListaCompra = new IntegranteListaCompra();
        integranteListaCompra.setListaCompraId(listaCompraSaved.getId());
        integranteListaCompra.setUsuarioId(listaCompraSaved.getUsuarioCreadorId());
        integranteListaCompra.setPorcentaje(100.0);
        integranteListaCompra.setEsCreador(Boolean.TRUE);
        integranteListaCompra.setEstado(ESTADOS_COLABORADORES.APROBADO.name());

        integranteListaCompraService.save(integranteListaCompra);

        return listaCompraMapper.toDto(listaCompraSaved);
    }
}
