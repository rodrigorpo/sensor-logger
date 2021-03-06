package com.br.rodrigo.pereira.sensorlogger.model.domain.requests;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentityUpdateRequest {
    @NotNull
    private String newUsername;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
