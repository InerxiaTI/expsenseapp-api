package com.inerxia.expensemateapi.controllers;

import com.inerxia.expensemateapi.dtos.IntegranteListaCompraDto;
import com.inerxia.expensemateapi.dtos.requests.AgregarColaboradorRequest;
import com.inerxia.expensemateapi.dtos.requests.AprobarRechazarColaboradorRequest;
import com.inerxia.expensemateapi.facades.IntegranteListaCompraFacade;
import com.inerxia.expensemateapi.utils.MessageResponse;
import com.inerxia.expensemateapi.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integrante")
public class IntegranteListaCompraController {

    private final IntegranteListaCompraFacade facade;
    private final Logger log = LoggerFactory.getLogger(IntegranteListaCompraController.class);

    public IntegranteListaCompraController(IntegranteListaCompraFacade facade) {
        this.facade = facade;
    }


    @PostMapping("/solicitud-agregar-colaborador")
    @Operation(summary = "Crear una solicitud para agregar colaborador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<IntegranteListaCompraDto>> agregarIntegranteColaborador(@RequestBody AgregarColaboradorRequest request) {
        var result = facade.agregarIntegranteColaborador(request);
        return ResponseEntity.ok(new StandardResponse<>(result, MessageResponse.COLLABORATOR_CREATED.getMessage(), MessageResponse.COLLABORATOR_CREATED.getDescription()));
    }

    @PostMapping("/aprobar-rechazar-colaborador")
    @Operation(summary = "Aprueba o rechaza la solicitud del colaborador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<IntegranteListaCompraDto>> aprobarRechazarColaborador(@RequestBody AprobarRechazarColaboradorRequest request) {
        var result = facade.aprobarRechazarColaborador(request);
        return ResponseEntity.ok(new StandardResponse<>(result, MessageResponse.COLLABORATOR_UPDATED.getMessage(), MessageResponse.COLLABORATOR_UPDATED.getDescription()));
    }
}