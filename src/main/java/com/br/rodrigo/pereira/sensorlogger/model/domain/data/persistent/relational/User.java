package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.Privileges;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String name;
    private String course;

    @NotNull
    private LocalDate birthday;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private Privileges privileges;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public User(String name, String course, LocalDate birthday, String username, String password, Privileges privileges, UserStatus userStatus) {
        this.name = name;
        this.course = course;
        this.birthday = birthday;
        this.username = username;
        this.password = password;
        this.privileges = privileges;
        this.userStatus = userStatus;
    }
}