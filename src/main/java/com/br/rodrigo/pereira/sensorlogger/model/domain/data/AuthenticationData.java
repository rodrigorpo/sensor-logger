package com.br.rodrigo.pereira.sensorlogger.model.domain.data;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.Privileges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationData {

    private String username;
    private Privileges privileges;
}
