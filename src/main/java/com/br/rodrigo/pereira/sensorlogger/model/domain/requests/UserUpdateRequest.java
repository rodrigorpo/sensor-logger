package com.br.rodrigo.pereira.sensorlogger.model.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String name;

    private String course;

    private LocalDate birthday;

    private String username;

    private String password;
}
