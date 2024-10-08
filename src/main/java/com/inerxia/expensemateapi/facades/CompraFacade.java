package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.CompraDto;
import com.inerxia.expensemateapi.dtos.requests.CrearCompraRequest;
import com.inerxia.expensemateapi.dtos.requests.EditarCompraRequest;
import com.inerxia.expensemateapi.dtos.requests.FiltroComprasRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaComprasResponse;
import com.inerxia.expensemateapi.entities.Compra;
import com.inerxia.expensemateapi.entities.ListaCompra;
import com.inerxia.expensemateapi.exceptions.RequestErrorException;
import com.inerxia.expensemateapi.mappers.CompraMapper;
import com.inerxia.expensemateapi.services.*;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import com.inerxia.expensemateapi.utils.enums.ESTADOS_LISTA_COMPRAS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CompraFacade {
    private final Logger log = LoggerFactory.getLogger(CompraFacade.class);
    private final CompraMapper compraMapper;
    private final CompraService compraService;
    private final ListaCompraService listaCompraService;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;
    private final IntegranteListaCompraService integranteListaCompraService;

    public CompraFacade(CompraMapper compraMapper, CompraService compraService, ListaCompraService listaCompraService, UsuarioService usuarioService, CategoriaService categoriaService, IntegranteListaCompraService integranteListaCompraService) {
        this.compraMapper = compraMapper;
        this.compraService = compraService;
        this.listaCompraService = listaCompraService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.integranteListaCompraService = integranteListaCompraService;
    }

    public Page<ConsultaComprasResponse> consultarComprasConFiltro(FiltroComprasRequest filtro, Pageable pageable) {
        CustomUtilService.ValidateRequired(filtro);
        CustomUtilService.ValidateRequired(filtro.getIdListaCompras());
        CustomUtilService.ValidateRequired(filtro.getIdUsuarioCompra());

        listaCompraService.validateListaCompra(filtro.getIdListaCompras());
        usuarioService.validateUsuario(filtro.getIdUsuarioCompra());

        return compraService.consultarComprasConFiltro(filtro, pageable);
    }

    public CompraDto crearCompra(CrearCompraRequest request) {
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getIdListaCompras());
        CustomUtilService.ValidateRequired(request.getIdCategoria());
        CustomUtilService.ValidateRequired(request.getIdUsuarioCompra());
        CustomUtilService.ValidateRequired(request.getIdUsuarioRegistro());
        CustomUtilService.ValidateRequired(request.getFechaCompra());
        CustomUtilService.ValidateRequired(request.getValor());

        usuarioService.validateUsuario(request.getIdUsuarioCompra());
        usuarioService.validateUsuario(request.getIdUsuarioRegistro());
        categoriaService.validateCategoria(request.getIdCategoria());
        integranteListaCompraService.findByListaCompraIdAndUsuarioId(request.getIdListaCompras(), request.getIdUsuarioCompra());
        integranteListaCompraService.findByListaCompraIdAndUsuarioId(request.getIdListaCompras(), request.getIdUsuarioRegistro());


        if (request.getValor() <= 0) {
            throw new RequestErrorException(MessageResponse.AMOUNT_NOT_ALLOWED);
        }

        var listaCompra = listaCompraService.findById(request.getIdListaCompras());
        if (!listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.PENDIENTE.name())) {
            throw new RequestErrorException(MessageResponse.ADD_PURCHASE_NOT_ALLOWED);
        }

        Compra compraSaved = compraService.save(buildCompraToCreate(request));

        calculateAndUpdateTotalCompras(request.getIdListaCompras(), listaCompra);

        return compraMapper.toDto(compraSaved);
    }

    public CompraDto editarCompra(EditarCompraRequest request) {
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getIdCompra());
        CustomUtilService.ValidateRequired(request.getIdUsuarioRegistro());

        Compra compra = compraService.findById(request.getIdCompra());
        if (Objects.nonNull(request.getIdUsuarioCompra())) {
            usuarioService.validateUsuario(request.getIdUsuarioCompra());
        }
        if (Objects.nonNull(request.getIdUsuarioRegistro())) {
            usuarioService.validateUsuario(request.getIdUsuarioRegistro());
        }
        if (Objects.nonNull(request.getIdCategoria())) {
            categoriaService.validateCategoria(request.getIdCategoria());
        }

        if (request.getValor() <= 0) {
            throw new RequestErrorException(MessageResponse.AMOUNT_NOT_ALLOWED);
        }

        var listaCompra = listaCompraService.findById(compra.getListaCompraId());
        if (!listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.PENDIENTE.name())) {
            throw new RequestErrorException(MessageResponse.EDIT_PURCHASE_NOT_ALLOWED);
        }

        Compra compraUpdated = compraService.update(buildCompraToUpdate(request, compra));

        calculateAndUpdateTotalCompras(compra.getListaCompraId(), listaCompra);

        return compraMapper.toDto(compraUpdated);
    }

    public void eliminarCompra(Integer idCompra) {
        CustomUtilService.ValidateRequired(idCompra);

        Compra compra = compraService.findById(idCompra);

        var listaCompra = listaCompraService.findById(compra.getListaCompraId());
        if (!listaCompra.getEstado().equals(ESTADOS_LISTA_COMPRAS.PENDIENTE.name())) {
            throw new RequestErrorException(MessageResponse.DELETE_PURCHASE_NOT_ALLOWED);
        }

        compraService.delete(idCompra);

        calculateAndUpdateTotalCompras(compra.getListaCompraId(), listaCompra);
    }

    private void calculateAndUpdateTotalCompras(Integer idListaCompra, ListaCompra listaCompra) {
        List<Compra> compras = compraService.consultarComprasByListaCompra(idListaCompra);
        Double totalCompras = calcularTotalCompras(compras);

        listaCompra.setTotalCompras(totalCompras);
        listaCompraService.update(listaCompra);
    }

    public Compra buildCompraToCreate(CrearCompraRequest request) {
        Compra compra = new Compra();
        compra.setListaCompraId(request.getIdListaCompras());
        compra.setCategoriaId(request.getIdCategoria());
        compra.setUsuarioCompraId(request.getIdUsuarioCompra());
        compra.setUsuarioRegistroId(request.getIdUsuarioRegistro());
        compra.setFechaCompra(request.getFechaCompra().atStartOfDay());
        compra.setDescripcion(request.getDescripcion());
        compra.setValor(request.getValor());
        compra.setFechaCreacion(LocalDateTime.now());
        return compra;
    }

    public Compra buildCompraToUpdate(EditarCompraRequest request, Compra compra) {
        compra.setCategoriaId(request.getIdCategoria());
        compra.setUsuarioCompraId(request.getIdUsuarioCompra());
        compra.setUsuarioRegistroId(request.getIdUsuarioRegistro());
        compra.setFechaCompra(request.getFechaCompra().atStartOfDay());
        compra.setDescripcion(request.getDescripcion());
        compra.setValor(request.getValor());
        return compra;
    }

    private Double calcularTotalCompras(List<Compra> compras) {
        return compras.stream()
                .mapToDouble(Compra::getValor)
                .sum();
    }
}
