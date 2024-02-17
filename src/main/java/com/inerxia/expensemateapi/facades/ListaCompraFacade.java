package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.ListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.*;
import com.inerxia.expensemateapi.dtos.responses.ConsultaDetalleCierreResponse;
import com.inerxia.expensemateapi.dtos.responses.IndicadoresResponse;
import com.inerxia.expensemateapi.entities.Compra;
import com.inerxia.expensemateapi.entities.DetalleCierre;
import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
import com.inerxia.expensemateapi.entities.ListaCompra;
import com.inerxia.expensemateapi.exceptions.BusinessException;
import com.inerxia.expensemateapi.mappers.ListaCompraMapper;
import com.inerxia.expensemateapi.services.*;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.GenerateRandomCodeService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_COLABORADORES;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_LISTA_COMPRAS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ListaCompraFacade {
    private final Logger log = LoggerFactory.getLogger(ListaCompraFacade.class);
    private final ListaCompraMapper listaCompraMapper;
    private final ListaCompraService listaCompraService;
    private final CompraService compraService;
    private final UsuarioService usuarioService;
    private final IntegranteListaCompraService integranteListaCompraService;
    private final DetalleCierreService detalleCierreService;

    public ListaCompraFacade(ListaCompraMapper listaCompraMapper, ListaCompraService listaCompraService, CompraService compraService, UsuarioService usuarioService, IntegranteListaCompraService integranteListaCompraService, DetalleCierreService detalleCierreService) {
        this.listaCompraMapper = listaCompraMapper;
        this.listaCompraService = listaCompraService;
        this.compraService = compraService;
        this.usuarioService = usuarioService;
        this.integranteListaCompraService = integranteListaCompraService;
        this.detalleCierreService = detalleCierreService;
    }

    private static Set<DeudaBidireccional> realizarCalculoBidireccional(Map<Integer, Double> totalValoresPorUsuariosCompra, List<IntegranteListaCompra> integrantes) {
        Set<DeudaBidireccional> deudoresPorAcreedor = calcularPorcentajeDeudoresPorAcreedor(totalValoresPorUsuariosCompra, integrantes);
        Set<DeudaBidireccional> deudasFinales = calcularRestaBidireccional(deudoresPorAcreedor);
        return deudasFinales;
    }

    private static Set<DeudaBidireccional> calcularRestaBidireccional(Set<DeudaBidireccional> deudoresPorAcreedor) {
        Map<Integer, List<DeudaBidireccional>> deudoresPorAcreedorGroup = deudoresPorAcreedor.stream()
                .collect(Collectors.groupingBy(DeudaBidireccional::getIdUsuarioAcreedor));

        Set<DeudaBidireccional> deudasFinales = new HashSet<>();
        deudoresPorAcreedorGroup.forEach((acreedor, deudores) -> {
            for (DeudaBidireccional deudor : deudores) {
                DeudaBidireccional deudaBidireccional = deudoresPorAcreedorGroup
                        .get(deudor.getIdUsuarioDeudor()).stream()
                        .filter(i -> i.getIdUsuarioDeudor().equals(acreedor)).findFirst().orElse(null);

                if (deudaBidireccional != null) {
                    double resta = deudor.getValorDeuda() - deudaBidireccional.getValorDeuda();
                    var deudaFinal = new DeudaBidireccional();
                    if (resta < 0) {
                        deudaFinal.setIdUsuarioAcreedor(deudor.getIdUsuarioDeudor());
                        deudaFinal.setIdUsuarioDeudor(deudor.getIdUsuarioAcreedor());
                    } else {
                        deudaFinal.setIdUsuarioAcreedor(deudor.getIdUsuarioAcreedor());
                        deudaFinal.setIdUsuarioDeudor(deudor.getIdUsuarioDeudor());
                    }
                    deudaFinal.setValorDeuda(Math.abs(resta));
                    deudasFinales.add(deudaFinal);
                }
            }
        });
        return deudasFinales;
    }

    private static Set<DeudaBidireccional> calcularPorcentajeDeudoresPorAcreedor(Map<Integer, Double> totalValoresPorUsuariosCompra, List<IntegranteListaCompra> integrantes) {
        Set<DeudaBidireccional> deudas = new HashSet<>();
        totalValoresPorUsuariosCompra.forEach((idUsuario, totalCompras) -> {
            for (IntegranteListaCompra integrante : integrantes) {
                var deuda = new DeudaBidireccional();
                if (integrante.getUsuarioId().equals(idUsuario)) {
                    continue;
                }
                deuda.setIdUsuarioAcreedor(idUsuario);
                deuda.setIdUsuarioDeudor(integrante.getUsuarioId());
                deuda.setValorDeuda(totalCompras * (integrante.getPorcentaje() / 100));
                deudas.add(deuda);
            }
        });
        return deudas;
    }

    public Page<ListaCompraDto> consultarListaComprasConFiltroConPaginacion(FilterListasComprasRequest filtro, Pageable pageable) {
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

    public ListaCompraDto inicializarListaCompras(Integer idListaCompras, Boolean back) {
        CustomUtilService.ValidateRequired(idListaCompras);

        ListaCompra listaCompra = listaCompraService.findById(idListaCompras);

        if (listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.PENDIENTE.name())) {
            throw new BusinessException(MessageResponse.PURCHASE_LIST_PENDING);
        }

        if (Objects.nonNull(back) && back) {
            if (!listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.EN_CIERRE.name())) {
                throw new BusinessException(MessageResponse.PURCHASE_LIST_NOT_CLOSED);
            }
            detalleCierreService.deleteByListaCompraId(listaCompra.getId());
            listaCompra.setFechaCierre(null);
        } else {
            if (!listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.CONFIGURANDO.name())) {
                throw new BusinessException(MessageResponse.PURCHASE_LIST_NOT_CONFIGURING);
            }

            var estados = List.of(ESTADOS_COLABORADORES.PENDIENTE);
            List<IntegranteListaCompra> integrantesPendientes = getIntegrantesPorEstado(idListaCompras, estados);

            if (!integrantesPendientes.isEmpty()) {
                throw new BusinessException(MessageResponse.HAS_PENDING_REQUESTS);
            }

            estados = List.of(ESTADOS_COLABORADORES.APROBADO);
            List<IntegranteListaCompra> integrantesAprobados = getIntegrantesPorEstado(idListaCompras, estados);

            Double totalPorcentajes = integranteListaCompraService.sumarPorcentajesIntegrantes(integrantesAprobados);

            if (totalPorcentajes != 100) {
                throw new BusinessException(MessageResponse.TOTAL_PERCENTAGES_MUST_BE_100_PERCENT);
            }
        }

        ListaCompra listaCompraUpdated = inicializarListaCompra(listaCompra);
        return listaCompraMapper.toDto(listaCompraUpdated);
    }

    private ListaCompra inicializarListaCompra(ListaCompra listaCompra) {
        listaCompra.setEstado(ESTADOS_LISTA_COMPRAS.PENDIENTE.name());
        listaCompra.setFechaInicializacion(LocalDateTime.now());
        return listaCompraService.update(listaCompra);
    }

    private List<IntegranteListaCompra> getIntegrantesPorEstado(Integer idListaCompras, List<ESTADOS_COLABORADORES> estados) {
        var filter = new FilterConsultaIntegrantesRequest();
        filter.setIdListaCompras(idListaCompras);
        if (!estados.isEmpty()) {
            filter.setEstados(estados.stream()
                    .map(Enum::name)
                    .collect(Collectors.toList()));
        }
        return integranteListaCompraService.consultarIntegrantesFilter(filter);
    }

    public ListaCompraDto cerrarListaCompras(Integer idListaCompras) {
        CustomUtilService.ValidateRequired(idListaCompras);

        ListaCompra listaCompra = listaCompraService.findById(idListaCompras);

        if (listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.EN_CIERRE.name())) {
            throw new BusinessException(MessageResponse.PURCHASE_LIST_CLOSED);
        }

        if (!listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.PENDIENTE.name())) {
            throw new BusinessException(MessageResponse.PURCHASE_LIST_NOT_PENDING);
        }

        List<Compra> compras = compraService.consultarComprasByListaCompra(listaCompra.getId());

        Map<Integer, Double> totalValoresPorUsuariosCompra = compras.stream()
                .collect(Collectors.groupingBy(Compra::getUsuarioCompraId,
                        Collectors.summingDouble(Compra::getValor)));

        var estados = List.of(ESTADOS_COLABORADORES.APROBADO);
        List<IntegranteListaCompra> integrantes = getIntegrantesPorEstado(idListaCompras, estados);

        Set<DeudaBidireccional> deudasFinales = realizarCalculoBidireccional(totalValoresPorUsuariosCompra, integrantes);

        ListaCompra listaCompraUpdated = cerrarListaCompra(listaCompra);

        generarDetalleCierre(deudasFinales, listaCompraUpdated);

        return listaCompraMapper.toDto(listaCompraUpdated);
    }

    private void generarDetalleCierre(Set<DeudaBidireccional> deudasFinales, ListaCompra listaCompraUpdated) {
        if (deudasFinales.size() > 0) {
            deudasFinales.forEach(deudaBidireccional -> {
                DetalleCierre detalleCierre = new DetalleCierre();
                detalleCierre.setListaCompraId(listaCompraUpdated.getId());
                detalleCierre.setUsuarioDeudorId(deudaBidireccional.getIdUsuarioDeudor());
                detalleCierre.setUsuarioAcreedorId(deudaBidireccional.getIdUsuarioAcreedor());
                detalleCierre.setTotalDeuda(deudaBidireccional.getValorDeuda());
                detalleCierre.setAprobado(false);
                detalleCierreService.save(detalleCierre);
            });
        }
    }

    private ListaCompra cerrarListaCompra(ListaCompra listaCompra) {
        listaCompra.setEstado(ESTADOS_LISTA_COMPRAS.EN_CIERRE.name());
        listaCompra.setFechaCierre(LocalDateTime.now());
        return listaCompraService.update(listaCompra);
    }

    public ListaCompraDto finalizarListaCompras(Integer idListaCompras) {
        CustomUtilService.ValidateRequired(idListaCompras);

        ListaCompra listaCompra = listaCompraService.findById(idListaCompras);

        if (listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.FINALIZADO.name())) {
            throw new BusinessException(MessageResponse.PURCHASE_LIST_FINALIZED);
        }

        if (!listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.EN_CIERRE.name())) {
            throw new BusinessException(MessageResponse.PURCHASE_LIST_NOT_CLOSED);
        }

        List<ConsultaDetalleCierreResponse> detalleCierre = detalleCierreService.consultarDetalleCierre(listaCompra.getId());

        boolean allApproved = detalleCierre.stream().allMatch(detalle -> Boolean.TRUE.equals(detalle.getAprobado()));
        if (!allApproved) {
            throw new BusinessException(MessageResponse.DEBTS_NOT_CLOSED);
        }

        ListaCompra listaCompraUpdated = finalizarListaCompra(listaCompra);
        return listaCompraMapper.toDto(listaCompraUpdated);
    }

    private ListaCompra finalizarListaCompra(ListaCompra listaCompra) {
        listaCompra.setEstado(ESTADOS_LISTA_COMPRAS.FINALIZADO.name());
        listaCompra.setFechaFinalizado(LocalDateTime.now());
        return listaCompraService.update(listaCompra);
    }

    public Page<ListaCompraDto> consultarListasSolicitadas(FilterSolicitudesRequest filter, Pageable pageable) {
        CustomUtilService.ValidateRequired(filter);
        CustomUtilService.ValidateRequired(filter.getIdUsuario());

        usuarioService.validateUsuario(filter.getIdUsuario());

        filter.setEstadoLista(ESTADOS_LISTA_COMPRAS.CONFIGURANDO.name());
        filter.setEstadoIntegrante(ESTADOS_COLABORADORES.PENDIENTE.name());
        filter.setEsCreador(false);

        return listaCompraService.consultarListasSolicitadas(filter, pageable);
    }

    public IndicadoresResponse consultarIndicadores(Integer idUsuario) {
        CustomUtilService.ValidateRequired(idUsuario);
        usuarioService.validateUsuario(idUsuario);

        var estadosLista = List.of(ESTADOS_LISTA_COMPRAS.PENDIENTE.name(), ESTADOS_LISTA_COMPRAS.EN_CIERRE.name());
        var filter = new FilterIndicadoresRequest();
        filter.setIdUsuario(idUsuario);
        filter.setEstadosLista(estadosLista);
        filter.setEstadoIntegrante(ESTADOS_COLABORADORES.APROBADO.name());
        var cantidadListasPendientes = listaCompraService.cantidadListasPendientes(filter);

        var dineroGastado = compraService.consultarDineroGastado(idUsuario);

        var estadoLista = ESTADOS_LISTA_COMPRAS.EN_CIERRE.name();
        filter = new FilterIndicadoresRequest();
        filter.setIdUsuario(idUsuario);
        filter.setEstadoLista(estadoLista);
        filter.setEstadoIntegrante(ESTADOS_COLABORADORES.APROBADO.name());
        var totalDeuda = listaCompraService.consultarTotalDeuda(filter);

        var indicadores = new IndicadoresResponse();
        indicadores.setCantidadListasPendientes(cantidadListasPendientes);
        indicadores.setTotalDineroGastado(dineroGastado);
        indicadores.setTotalDeuda(totalDeuda);
        return indicadores;
    }
}
