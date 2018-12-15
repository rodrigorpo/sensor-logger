package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Measures;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import com.br.rodrigo.pereira.sensorlogger.util.HttpDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {

    private LocalDateTime instant;

    private OperationType operationType;

    private User user;

    private Measures measures;

    private Location location;

    private HttpDocument httpStatus;

    private RuntimeException exception;

    public Log(OperationType operationType, User user, Measures measures, Location location, HttpDocument httpDocument, RuntimeException exception) {
        this.instant = LocalDateTime.now();
        this.operationType = operationType;
        this.user = user;
        this.measures = measures;
        this.location = location;
        this.httpStatus = httpDocument;
        this.exception = exception;
    }

    public Log(OperationType operationType, User user, Measures measures, Location location, HttpDocument httpDocument) {
        this.instant = LocalDateTime.now();
        this.operationType = operationType;
        this.user = user;
        this.measures = measures;
        this.location = location;
        this.httpStatus = httpDocument;
    }
}
