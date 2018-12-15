package com.br.rodrigo.pereira.sensorlogger.model.domain.requests;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.Privileges;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @NotNull
    private String name;
    @NotNull
    private String course;
    @NotNull
    private LocalDate birthday;
    @NotNull
    private UserStatus status;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Privileges privileges;
}
