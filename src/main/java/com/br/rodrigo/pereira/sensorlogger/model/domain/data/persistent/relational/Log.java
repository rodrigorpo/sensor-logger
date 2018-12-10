package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational;

import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.util.converter.LocalDateTimeAttributeConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
public class Log implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLog;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime instant;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "userId"))
    private User user;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "measureId"))
    private Measures measures;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "locationId"))
    private Location location;

    public Log(OperationType operationType, User user, Measures measures, Location location, Long httpStatus, String httpStatusReason){
        this.operationType = operationType;
        this.user = user;
        this.measures = measures;
        this.location = location;
        this.httpStatus = httpStatus;
        this.httpStatusReason = httpStatusReason;
    }

    private Long httpStatus;

    private String httpStatusReason;

    @PrePersist
    private void prePersist() {
        this.instant = LocalDateTime.now();
    }
}
