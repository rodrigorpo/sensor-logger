package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.Privileges;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "usersIdentity")
@Data
@NoArgsConstructor
public class UserIdentity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdentityId;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private Privileges privileges;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public UserIdentity(String username, String password, Privileges privileges, UserStatus userStatus) {
        this.username = username;
        this.password = password;
        this.privileges = privileges;
        this.userStatus = userStatus;
    }
}
