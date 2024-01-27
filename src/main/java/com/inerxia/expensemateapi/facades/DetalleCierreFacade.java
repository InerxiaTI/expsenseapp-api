package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.responses.ConsultaDetalleCierreResponse;
import com.inerxia.expensemateapi.entities.ListaCompra;
import com.inerxia.expensemateapi.mappers.DetalleCierreMapper;
import com.inerxia.expensemateapi.services.DetalleCierreService;
import com.inerxia.expensemateapi.services.ListaCompraService;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DetalleCierreFacade {
    private final Logger log = LoggerFactory.getLogger(DetalleCierreFacade.class);
    private final DetalleCierreService detalleCierreService;
    private final DetalleCierreMapper detalleCierreMapper;
    private final ListaCompraService listaCompraService;

    public DetalleCierreFacade(DetalleCierreService detalleCierreService, DetalleCierreMapper detalleCierreMapper, ListaCompraService listaCompraService) {
        this.detalleCierreService = detalleCierreService;
        this.detalleCierreMapper = detalleCierreMapper;
        this.listaCompraService = listaCompraService;
    }
    public List<ConsultaDetalleCierreResponse> consultarDetalleCierre(Integer idListaCompras){
        CustomUtilService.ValidateRequired(idListaCompras);

        ListaCompra listaCompra = listaCompraService.findById(idListaCompras);

        return detalleCierreService.consultarDetalleCierre(listaCompra.getId());
    }
}
