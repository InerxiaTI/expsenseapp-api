package com.inerxia.expensemateapi.controllers;

import com.inerxia.expensemateapi.dtos.ListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.CrearListaCompraRequest;
import com.inerxia.expensemateapi.dtos.requests.FilterListasComprasRequest;
import com.inerxia.expensemateapi.facades.ListaCompraFacade;
import com.inerxia.expensemateapi.utils.MessageResponse;
import com.inerxia.expensemateapi.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lista-compra")
public class ListaCompraController {

    private final ListaCompraFacade facade;
    private final Logger log = LoggerFactory.getLogger(ListaCompraController.class);

    public ListaCompraController(ListaCompraFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/filter")
    @Operation(summary = "Consulta las listas de compras con filtros y paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data found successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "Page you want to retrieve (0..N)", name = "page",
                    content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
            @Parameter(in = ParameterIn.QUERY, description = "Number of records per page.", name = "size",
                    content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
            @Parameter(in = ParameterIn.QUERY, description = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.", name = "sort",
                    content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))))
    })
    public ResponseEntity<StandardResponse<Page<ListaCompraDto>>> consultarListaComprasConFiltroConPaginacion(
            @RequestBody FilterListasComprasRequest filtro, Pageable pageable) {
        var compras = facade.consultarListaComprasConFiltroConPaginacion(filtro, pageable);
        return ResponseEntity.ok(new StandardResponse<>(compras));
    }

    @PostMapping("/crear-lista-compra")
    @Operation(summary = "Crear una lista compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<ListaCompraDto>> crearListaCompra(@RequestBody CrearListaCompraRequest request) {
        var listaCompra = facade.crearListaCompra(request);
        return ResponseEntity.ok(new StandardResponse<>(listaCompra, MessageResponse.PURCHASE_LIST_CREATED.getMessage(), MessageResponse.PURCHASE_LIST_CREATED.getDescription()));
    }

    @PutMapping("/inicializar-lista-compras/{idListaCompras}")
    @Operation(summary = "Inicializa la lista de compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<ListaCompraDto>> inicializarListaCompras(@PathVariable Integer idListaCompras) {
        var result = facade.inicializarListaCompras(idListaCompras);
        return ResponseEntity.ok(new StandardResponse<>(result, MessageResponse.PURCHASE_LIST_UPDATED.getMessage(), MessageResponse.PURCHASE_LIST_UPDATED.getDescription()));
    }

    @PutMapping("/cerrar-lista-compras/{idListaCompras}")
    @Operation(summary = "Cierra la lista de compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<ListaCompraDto>> cerrarListaCompras(@PathVariable Integer idListaCompras) {
        var result = facade.cerrarListaCompras(idListaCompras);
        return ResponseEntity.ok(new StandardResponse<>(result, MessageResponse.PURCHASE_LIST_UPDATED.getMessage(), MessageResponse.PURCHASE_LIST_UPDATED.getDescription()));
    }
}