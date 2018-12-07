package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational;

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

    private String name;

    private String course;

    private LocalDate birthday;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "users_identity")
    private UserIdentity userIdentity;

    public User(String name, String course, LocalDate birthday, UserIdentity userIdentity){
        this.name = name;
        this.course = course;
        this.birthday = birthday;
        this.userIdentity = userIdentity;
    }
}