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
    COLLABORATOR_NOT_FOUND_EXCEPTION("COLLABORATOR_NOT_FOUND_EXCEPTION", "Collaborator could not be found"),
    CLOSURE_DETAIL_NOT_FOUND_EXCEPTION("CLOSURE_DETAIL_NOT_FOUND_EXCEPTION", "Closure detail could not be found"),


    AMOUNT_NOT_ALLOWED("AMOUNT_NOT_ALLOWED", "Amount not allowed."),
    ADD_COLLABORATOR_NOT_ALLOWED("ADD_COLLABORATOR_NOT_ALLOWED", "Add collaborator not allowed."),
    DUPLICATE_USER_ON_PURCHASE_LIST("DUPLICATE_USER_ON_PURCHASE_LIST", "Duplicate user on purchase list."),
    PARTNER_REQUEST_REJECTED("PARTNER_REQUEST_REJECTED", "Partner request rejected."),
    REQUEST_HAS_NOT_BEEN_APPROVED("REQUEST_HAS_NOT_BEEN_APPROVED", "Request has not been approved."),
    ADD_PURCHASE_NOT_ALLOWED("ADD_PURCHASE_NOT_ALLOWED", "Add purchase not allowed."),
    EDIT_PURCHASE_NOT_ALLOWED("EDIT_PURCHASE_NOT_ALLOWED", "Edit purchase not allowed."),
    DELETE_PURCHASE_NOT_ALLOWED("DELETE_PURCHASE_NOT_ALLOWED", "Delete purchase not allowed."),
    USER_NOT_ACTIVE("USER_NOT_ACTIVE", "The user is not active."),
    NOT_ALLOWED_ENABLE("NOT_ALLOWED_ENABLE", "Approve/Reject is not allowed because it is not the creating user."),
    ENABLE_COLLABORATOR_NOT_ALLOWED("ENABLE_COLLABORATOR_NOT_ALLOWED", "Enable collaborator not allowed."),
    CHANGE_PERCENTAGE_NOT_ALLOWED("CHANGE_PERCENTAGE_NOT_ALLOWED", "Change percentage not allowed."),
    PERCENT_NOT_ALLOWED("PERCENT_NOT_ALLOWED", "Percent not allowed."),
    CHANGE_REQUEST_STATUS_TO_CREATOR_NOT_ALLOWED("CHANGE_REQUEST_STATUS_TO_CREATOR_NOT_ALLOWED", "Changing the status of the creator's request is not allowed."),
    HAS_PENDING_REQUESTS("HAS_PENDING_REQUESTS", "It is not allowed to start the list with pending requests."),
    TOTAL_PERCENTAGES_MUST_BE_100_PERCENT("TOTAL_PERCENTAGES_MUST_BE_100_PERCENT", "Total percentages must be 100 percent."),
    CATEGORY_HAS_ASSOCIATED_PURCHASES("CATEGORY_HAS_ASSOCIATED_PURCHASES", "Category has associated purchases."),
    PURCHASE_LIST_CLOSED("PURCHASE_LIST_CLOSED", "The purchase list is already closed."),
    PURCHASE_LIST_NOT_CLOSED("PURCHASE_LIST_NOT_CLOSED", "The purchase list must be in EN_CIERRE status"),


    /*Success*/
    PURCHASE_CREATED("PURCHASE_CREATED", "Purchase created successfully"),
    CATEGORY_CREATED("CATEGORY_CREATED", "Category created successfully"),
    PURCHASE_LIST_CREATED("PURCHASE_LIST_CREATED", "Purchase list created successfully"),
    PURCHASE_LIST_UPDATED("PURCHASE_LIST_UPDATED", "Purchase list updated successfully"),
    PURCHASE_UPDATED("PURCHASE_UPDATED", "Purchase updated successfully"),
    CATEGORY_UPDATED("CATEGORY_UPDATED", "Category updated successfully"),
    PURCHASE_DELETED("PURCHASE_DELETED", "Purchase deleted successfully"),
    CATEGORY_DELETED("CATEGORY_DELETED", "Category deleted successfully"),
    COLLABORATOR_CREATED("COLLABORATOR_CREATED", "Collaborator created successfully"),
    COLLABORATOR_UPDATED("COLLABORATOR_UPDATED", "Collaborator updated successfully"),
    ;

    private final String message;
    private final String description;

    MessageResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
