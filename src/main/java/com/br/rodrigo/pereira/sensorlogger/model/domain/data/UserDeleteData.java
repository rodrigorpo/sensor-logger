package com.br.rodrigo.pereira.sensorlogger.model.domain.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteData {
    private String username;
    private String password;
    private LocalDate birthday;
}
