package com.br.rodrigo.pereira.sensorlogger.model.domain.responses;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.Privileges;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long userId;

    private String name;

    private String course;

    private LocalDate birthday;

    private String username;

    private Privileges privileges;

    private UserStatus userStatus;

}
