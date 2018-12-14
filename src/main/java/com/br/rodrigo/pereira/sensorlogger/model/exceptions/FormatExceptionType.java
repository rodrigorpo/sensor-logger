package com.br.rodrigo.pereira.sensorlogger.model.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FormatExceptionType {
    private String message;
    private String error;
    private LocalDateTime time;

    public FormatExceptionType(String message, String error, LocalDateTime time) {
        this.message = message;
        this.error = error;
        this.time = time;
    }
}
