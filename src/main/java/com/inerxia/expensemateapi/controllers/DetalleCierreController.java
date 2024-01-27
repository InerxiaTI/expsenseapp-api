package com.inerxia.expensemateapi.controllers;

import com.inerxia.expensemateapi.dtos.responses.ConsultaDetalleCierreResponse;
import com.inerxia.expensemateapi.facades.DetalleCierreFacade;
import com.inerxia.expensemateapi.utils.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/detalle-cierre")
public class DetalleCierreController {

    private final DetalleCierreFacade facade;
    private final Logger log = LoggerFactory.getLogger(DetalleCierreController.class);

    public DetalleCierreController(DetalleCierreFacade facade) {
        this.facade = facade;
    }


    @PostMapping("/consultar-detalle-cierre/{idListaCompras}")
    @Operation(summary = "Consulta el detalle cierre de una lista de compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data inserted successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<List<ConsultaDetalleCierreResponse>>> consultarDetalleCierre(@PathVariable Integer idListaCompras) {
        var result = facade.consultarDetalleCierre(idListaCompras);
        return ResponseEntity.ok(new StandardResponse<>(result));
    }
}