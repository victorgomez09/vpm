package com.vira.vpm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AttributeException extends Exception {

    public AttributeException(String message) {
        super(message);
    }
}
