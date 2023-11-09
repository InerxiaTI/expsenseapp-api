package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.ListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.FilterListasComprasRequest;
import com.inerxia.expensemateapi.mappers.ListaCompraMapper;
import com.inerxia.expensemateapi.services.ListaCompraService;
import com.inerxia.expensemateapi.services.UsuarioService;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ListaCompraFacade {
    private final Logger log = LoggerFactory.getLogger(ListaCompraFacade.class);
    private final ListaCompraMapper listaCompraMapper;
    private final ListaCompraService listaCompraService;
    private final UsuarioService usuarioService;

    public ListaCompraFacade(ListaCompraMapper listaCompraMapper, ListaCompraService listaCompraService, UsuarioService usuarioService) {
        this.listaCompraMapper = listaCompraMapper;
        this.listaCompraService = listaCompraService;
        this.usuarioService = usuarioService;
    }

    public Page<ListaCompraDto> consultarListaComprasConFiltroConPaginacion(FilterListasComprasRequest filtro, Pageable pageable){
        CustomUtilService.ValidateRequired(filtro);
        CustomUtilService.ValidateRequired(filtro.getUsuario());

        usuarioService.validateUsuario(filtro.getUsuario());
        usuarioService.validateUsuarioActivo(filtro.getUsuario());

        return listaCompraService.consultarListaCompras(filtro, pageable);
    }
}
