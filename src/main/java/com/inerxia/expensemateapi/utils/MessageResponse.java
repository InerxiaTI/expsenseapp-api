package com.inerxia.expensemateapi.utils;

import lombok.Getter;

@Getter
public enum MessageResponse {
    MISSING_REQUIRED_FIELD("MISSING_REQUIRED_FIELD", "Required field is missing"),

    /*NOT_FOUND_EXCEPTION*/
    USER_NOT_FOUND_EXCEPTION("USER_NOT_FOUND_EXCEPTION", "User could not be found"),
    LIST_NOT_FOUND_EXCEPTION("LIST_NOT_FOUND_EXCEPTION", "Purchase list could not be found"),
    CATEGORY_NOT_FOUND_EXCEPTION("CATEGORY_NOT_FOUND_EXCEPTION", "Category could not be found"),
    PURCHASE_NOT_FOUND_EXCEPTION("PURCHASE_NOT_FOUND_EXCEPTION", "Purchase could not be found"),


    AMOUNT_NOT_ALLOWED("AMOUNT_NOT_ALLOWED", "Amount not allowed."),
    ADD_PURCHASE_NOT_ALLOWED("ADD_PURCHASE_NOT_ALLOWED", "Add purchase not allowed."),
    EDIT_PURCHASE_NOT_ALLOWED("EDIT_PURCHASE_NOT_ALLOWED", "Edit purchase not allowed."),
    DELETE_PURCHASE_NOT_ALLOWED("DELETE_PURCHASE_NOT_ALLOWED", "Delete purchase not allowed."),
    USER_NOT_ACTIVE("USER_NOT_ACTIVE", "The user is not active."),


    /*Success*/
    PURCHASE_CREATED("PURCHASE_CREATED", "Purchase created successfully"),
    PURCHASE_LIST_CREATED("PURCHASE_LIST_CREATED", "Purchase list created successfully"),
    PURCHASE_UPDATED("PURCHASE_UPDATED", "Purchase updated successfully"),
    PURCHASE_DELETED("PURCHASE_DELETED", "Purchase deleted successfully"),
    ;

    private final String message;
    private final String description;

    MessageResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
