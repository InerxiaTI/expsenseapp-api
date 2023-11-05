package com.inerxia.expensemateapi.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardResponse<T> {

    private String description;
    private String message;
    private T body;

    public StandardResponse() {
    }

    public StandardResponse(T body, String message) {
        this.message = message;
        this.body = body;
    }

    public StandardResponse(T body) {
        this.body = body;
    }

    public StandardResponse(String message, String description) {
        this.description = description;
        this.message = message;
    }

    public StandardResponse(T body, String message, String description) {
        this.description = description;
        this.message = message;
        this.body = body;
    }
}
