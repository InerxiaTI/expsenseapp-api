package com.inerxia.expensemateapi.exceptions;


import com.inerxia.expensemateapi.utils.MessageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestErrorException extends RuntimeException {

    private String description;

    public RequestErrorException(String exception) {
        super(exception);
    }

    public RequestErrorException(String message, Exception exception) {
        super(message, exception);
    }

    public RequestErrorException(MessageResponse message) {
        super(message.getMessage());
        this.description = message.getDescription();
    }
}
