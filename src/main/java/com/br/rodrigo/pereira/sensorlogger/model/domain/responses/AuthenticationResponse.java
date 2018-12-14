package com.br.rodrigo.pereira.sensorlogger.model.domain.responses;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.Privileges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String username;

    private Privileges privileges;
}
