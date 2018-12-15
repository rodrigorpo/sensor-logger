package com.br.rodrigo.pereira.sensorlogger.model.domain.data;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdentityUpdateData {
    private String oldUsername;
    private String newUsername;
    private String oldPassword;
    private String newPassword;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
