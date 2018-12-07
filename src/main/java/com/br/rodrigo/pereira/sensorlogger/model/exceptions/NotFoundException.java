package com.br.rodrigo.pereira.sensorlogger.model.exceptions;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    static final long serialVersionUID = -7034897190745766939L;
    private String error;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String error) {
        super(message);
        this.error = error;
    }
}
