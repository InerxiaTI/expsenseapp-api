package com.inerxia.expensemateapi.utils;

import lombok.Getter;

@Getter
public enum MessageResponse {
    USER_NOT_FOUND_EXCEPTION("user_not_found", "User could not be found"),
    ;

    private final String message;
    private final String description;

    MessageResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
