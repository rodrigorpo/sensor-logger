package com.br.rodrigo.pereira.sensorlogger.model.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateData {
    private String name;
    private String course;
    private LocalDate birthday;
    private String username;
    private String password;
}
