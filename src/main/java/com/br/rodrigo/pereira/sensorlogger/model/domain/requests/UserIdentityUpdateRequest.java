package com.br.rodrigo.pereira.sensorlogger.model.domain.requests;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentityUpdateRequest {

    private String oldUsername;

    private String newUsername;

    private String oldPassword;

    private String newPassword;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
