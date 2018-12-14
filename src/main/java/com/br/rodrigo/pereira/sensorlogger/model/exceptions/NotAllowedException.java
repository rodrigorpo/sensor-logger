package com.br.rodrigo.pereira.sensorlogger.model.exceptions;

import lombok.Data;

@Data
public class NotAllowedException extends RuntimeException {
    static final long serialVersionUID = -7034897190745766939L;

    private String error;

    public NotAllowedException(String message, String error) {
        super(message);
        this.error = error;
    }
}
