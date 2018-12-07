package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.documents;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Measures;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.UserIdentity;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.util.converter.LocalDateTimeAttributeConverter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Document
public class Log implements Serializable {

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime instant;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "userId"))
    private UserIdentity userIdentity;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "measureId"))
    private Measures measures;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "locationId"))
    private Location location;

    private Long httpStatus;

    private String httpStatusReason;
}