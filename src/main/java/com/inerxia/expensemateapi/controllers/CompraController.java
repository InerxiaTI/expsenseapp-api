package com.inerxia.expensemateapi.controllers;

import com.inerxia.expensemateapi.facades.CompraFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compra")
public class CompraController {

    private final CompraFacade facade;
    private final Logger log = LoggerFactory.getLogger(CompraController.class);

    public CompraController(CompraFacade facade) {
        this.facade = facade;
    }

}