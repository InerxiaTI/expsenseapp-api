package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.DetalleCierreDto;
import com.inerxia.expensemateapi.dtos.requests.CambiarEstadoDetalleCierreRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaDetalleCierreResponse;
import com.inerxia.expensemateapi.entities.DetalleCierre;
import com.inerxia.expensemateapi.entities.ListaCompra;
import com.inerxia.expensemateapi.exceptions.BusinessException;
import com.inerxia.expensemateapi.mappers.DetalleCierreMapper;
import com.inerxia.expensemateapi.services.DetalleCierreService;
import com.inerxia.expensemateapi.services.ListaCompraService;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_LISTA_COMPRAS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    public List<ConsultaDetalleCierreResponse> consultarDetalleCierre(Integer idListaCompras) {
        CustomUtilService.ValidateRequired(idListaCompras);

        ListaCompra listaCompra = listaCompraService.findById(idListaCompras);

        return detalleCierreService.consultarDetalleCierre(listaCompra.getId());
    }

    public DetalleCierreDto cambiarEstadoAprobadoDetalleCierre(CambiarEstadoDetalleCierreRequest request) {
        CustomUtilService.ValidateRequired(request.getIdDetalleCierre());
        CustomUtilService.ValidateBooleanRequired(request.getAprobado());

        DetalleCierre detalleCierre = detalleCierreService.findById(request.getIdDetalleCierre());

        ListaCompra listaCompra = listaCompraService.findById(detalleCierre.getListaCompraId());

        if (!listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.EN_CIERRE.name())) {
            throw new BusinessException(MessageResponse.PURCHASE_LIST_NOT_CLOSED);
        }

        detalleCierre.setAprobado(request.getAprobado());
        detalleCierre.setFechaAprobacion(request.getAprobado() ? LocalDateTime.now() : null);

        DetalleCierre detalleCierreUpdated = detalleCierreService.update(detalleCierre);
        return detalleCierreMapper.toDto(detalleCierreUpdated);
    }
}
