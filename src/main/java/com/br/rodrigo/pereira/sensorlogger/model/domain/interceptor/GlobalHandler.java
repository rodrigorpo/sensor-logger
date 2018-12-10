package com.br.rodrigo.pereira.sensorlogger.model.domain.interceptor;

import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.FormatExceptionType;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.InvalidCredentialsException;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public FormatExceptionType handleAuthenticationNotFoundException(NotFoundException e) {
//        LOGGER.error("AuthenticationException:{}", e.getMessage(), e);
        return new FormatExceptionType(e.getMessage(), e.getError(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BusinessException.class)
    public FormatExceptionType handleAuthenticationUnprocessableEntityException(BusinessException e) {
//        LOGGER.error("AuthenticationException:{}", e.getMessage(), e);
        return new FormatExceptionType(e.getMessage(), e.getError(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public FormatExceptionType handleAuthenticationGenericException(Exception e) {
//        LOGGER.error("AuthenticationException:{}", e.getMessage(), e);
        return new FormatExceptionType(e.getMessage(), "BAD_REQUEST", LocalDateTime.now());
    }
}
