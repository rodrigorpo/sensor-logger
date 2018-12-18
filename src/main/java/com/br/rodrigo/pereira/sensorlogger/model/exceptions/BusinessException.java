package com.br.rodrigo.pereira.sensorlogger.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    static final long serialVersionUID = -7034897190745766939L;
    private String error;

    public BusinessException() {
        super();
    }

    public BusinessException(String message, String error) {
        super(message);
        this.error = error;
    }
}