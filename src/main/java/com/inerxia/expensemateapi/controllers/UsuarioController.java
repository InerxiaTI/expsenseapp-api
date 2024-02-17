package com.inerxia.expensemateapi.controllers;

import com.inerxia.expensemateapi.dtos.UsuarioDto;
import com.inerxia.expensemateapi.dtos.requests.GetUsuarioRequest;
import com.inerxia.expensemateapi.facades.UsuarioFacade;
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
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioFacade facade;
    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    public UsuarioController(UsuarioFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/login")
    @Operation(summary = "Consultar usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data found successfully"),
            @ApiResponse(responseCode = "400", description = "The request is invalid"),
            @ApiResponse(responseCode = "500", description = "Internal error processing response"),
    })
    public ResponseEntity<StandardResponse<UsuarioDto>> getUsuarioByCorreoWithPass(@RequestBody GetUsuarioRequest request) {
        var usuario = facade.getUsuarioByCorreoWithPass(request);
        return ResponseEntity.ok(new StandardResponse<>(usuario));
    }
}