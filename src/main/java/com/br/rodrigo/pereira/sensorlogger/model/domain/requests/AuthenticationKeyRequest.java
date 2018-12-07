package com.br.rodrigo.pereira.sensorlogger.model.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationKeyRequest {
    private String key;
}
