package com.inerxia.expensemateapi.controllers;

import com.inerxia.expensemateapi.dtos.CategoriaDto;
import com.inerxia.expensemateapi.dtos.requests.CrearCategoriaRequest;
import com.inerxia.expensemateapi.dtos.requests.EditarCategoriaRequest;
import com.inerxia.expensemateapi.dtos.requests.FiltroCategoriaRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaCategoriaResponse;
import com.inerxia.expensemateapi.facades.CategoriaFacade;
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

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaFacade facade;
    private final Logger log = LoggerFactory.getLogger(CategoriaController.class);

    public CategoriaController(CategoriaFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/filter")
    @Operation(summary = "Consulta las categorías con filtros y paginación")
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
    public ResponseEntity<StandardResponse<Page<ConsultaCategoriaResponse>>> consultarCategoriasConFiltro(
            @RequestBody FiltroCategoriaRequest filtro, Pageable pageable) {
        var result = facade.consultarCategoriasConFiltro(filtro, pageable);
        return ResponseEntity.ok(new StandardResponse<>(result));
    }

    @GetMapping("/filter-categorias-by-creador/{usuarioCreadorId}")
    @Operation(summary = "Consulta las categorías públicas del creador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<List<CategoriaDto>>> consultarCategoriasDelCreadorConFiltro(
            @PathVariable Integer usuarioCreadorId) {
        var result = facade.consultarCategoriasDelCreadorConFiltro(usuarioCreadorId);
        return ResponseEntity.ok(new StandardResponse<>(result));
    }

    @PostMapping("/crear-categoria")
    @Operation(summary = "Crear una categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<CategoriaDto>> crearCategoria(@RequestBody CrearCategoriaRequest request) {
        var result = facade.crearCategoria(request);
        return ResponseEntity.ok(new StandardResponse<>(result, MessageResponse.CATEGORY_CREATED.getMessage(), MessageResponse.CATEGORY_CREATED.getDescription()));
    }

    @PutMapping("/editar-categoria")
    @Operation(summary = "Editar una categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data updated successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<CategoriaDto>> editarCategoria(@RequestBody EditarCategoriaRequest request) {
        var result = facade.editarCategoria(request);
        return ResponseEntity.ok(new StandardResponse<>(result, MessageResponse.CATEGORY_UPDATED.getMessage(), MessageResponse.CATEGORY_UPDATED.getDescription()));
    }
}