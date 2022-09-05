package com.vira.vpm.common.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vira.vpm.common.dto.MessageDto;
import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;

@RestControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(AttributeException.class)
    public ResponseEntity<MessageDto> throwAttributeException(AttributeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(MessageDto.builder().statusCode(HttpStatus.CONFLICT).message(e.getMessage()).build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageDto> throwNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageDto.builder().statusCode(HttpStatus.NOT_FOUND).message(e.getMessage()).build());
    }
}
