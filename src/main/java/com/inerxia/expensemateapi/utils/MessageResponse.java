package com.inerxia.expensemateapi.utils;

import lombok.Getter;

@Getter
public enum MessageResponse {
    MISSING_REQUIRED_FIELD("missing_required_field", "Required field is missing"),
    USER_NOT_FOUND_EXCEPTION("user_not_found", "User could not be found"),
    LIST_NOT_FOUND_EXCEPTION("list_not_found", "Purchase list could not be found"),
    ;

    private final String message;
    private final String description;

    MessageResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
