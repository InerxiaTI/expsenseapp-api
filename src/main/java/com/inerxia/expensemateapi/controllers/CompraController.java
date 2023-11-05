package com.inerxia.expensemateapi.controllers;

import com.inerxia.expensemateapi.dtos.CompraDto;
import com.inerxia.expensemateapi.dtos.requests.CrearCompraRequest;
import com.inerxia.expensemateapi.dtos.requests.EditarCompraRequest;
import com.inerxia.expensemateapi.dtos.requests.FiltroComprasRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaComprasResponse;
import com.inerxia.expensemateapi.facades.CompraFacade;
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
@RequestMapping("/compra")
public class CompraController {

    private final CompraFacade facade;
    private final Logger log = LoggerFactory.getLogger(CompraController.class);

    public CompraController(CompraFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/filter")
    @Operation(summary = "Consulta las compras con filtros y paginación")
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
    public ResponseEntity<StandardResponse<Page<ConsultaComprasResponse>>> consultarComprasConFiltro(
            @RequestBody FiltroComprasRequest filtro, Pageable pageable) {
        var compras = facade.consultarComprasConFiltro(filtro, pageable);
        return ResponseEntity.ok(new StandardResponse<>(compras));
    }

    @PostMapping("/crear-compra")
    @Operation(summary = "Crear una compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<CompraDto>> crearCompra(@RequestBody CrearCompraRequest request) {
        var compra = facade.crearCompra(request);
        return ResponseEntity.ok(new StandardResponse<>(compra, MessageResponse.PURCHASE_CREATED.getMessage()));
    }

    @PutMapping("/editar-compra")
    @Operation(summary = "Editar una compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<CompraDto>> editarCompra(@RequestBody EditarCompraRequest request) {
        var compra = facade.editarCompra(request);
        return ResponseEntity.ok(new StandardResponse<>(compra, MessageResponse.PURCHASE_UPDATED.getMessage()));
    }
}