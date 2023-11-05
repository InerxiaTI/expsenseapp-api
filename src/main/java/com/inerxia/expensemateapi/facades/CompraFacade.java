package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.requests.FiltroComprasRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaComprasResponse;
import com.inerxia.expensemateapi.mappers.CompraMapper;
import com.inerxia.expensemateapi.services.CompraService;
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
public class CompraFacade {
    private final Logger log = LoggerFactory.getLogger(CompraFacade.class);
    private final CompraMapper compraMapper;
    private final CompraService compraService;
    private final ListaCompraService listaCompraService;
    private final UsuarioService usuarioService;

    public CompraFacade(CompraMapper compraMapper, CompraService compraService, ListaCompraService listaCompraService, UsuarioService usuarioService) {
        this.compraMapper = compraMapper;
        this.compraService = compraService;
        this.listaCompraService = listaCompraService;
        this.usuarioService = usuarioService;
    }

    public Page<ConsultaComprasResponse> consultarComprasConFiltro(FiltroComprasRequest filtro, Pageable pageable){
        CustomUtilService.ValidateRequired(filtro);
        CustomUtilService.ValidateRequired(filtro.getIdListaCompras());
        CustomUtilService.ValidateRequired(filtro.getIdUsuarioCompra());

        listaCompraService.listaCompraExists(filtro.getIdListaCompras());
        usuarioService.usuarioExists(filtro.getIdUsuarioCompra());

        return compraService.consultarComprasConFiltro(filtro, pageable);
    }
}
