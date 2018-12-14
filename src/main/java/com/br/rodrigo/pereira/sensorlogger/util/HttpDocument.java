package com.br.rodrigo.pereira.sensorlogger.util;

import org.springframework.http.HttpStatus;

public class HttpDocument {
    private Long http_code;
    private String http_name;

    public HttpDocument(HttpStatus httpStatus){
        this.http_code = Long.valueOf(httpStatus.value());
        this.http_name = httpStatus.getReasonPhrase().toString();
    }
}
