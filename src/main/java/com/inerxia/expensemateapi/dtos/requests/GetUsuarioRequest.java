package com.inerxia.expensemateapi.dtos.requests;

import lombok.Data;

@Data
public class GetUsuarioRequest {
    private String email;
    private String pass;
}
