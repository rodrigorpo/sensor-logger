package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Measure;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.util.HttpDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {
    @Id
    private String id;
    private LocalDateTime instant;
    private OperationType operationType;
    private User user;
    private Measure measure;
    private Location location;
    private HttpDocument httpDocument;
    private RuntimeException runtimeException;

    public Log(OperationType operationType, User user, Measure measure, Location location, HttpDocument httpDocument, RuntimeException runtimeException) {
        this.instant = LocalDateTime.now();
        this.operationType = operationType;
        this.user = user;
        this.measure = measure;
        this.location = location;
        this.httpDocument = httpDocument;
        this.runtimeException = runtimeException;
    }

    public Log(OperationType operationType, User user, Measure measure, Location location, HttpDocument httpDocument) {
        this.instant = LocalDateTime.now();
        this.operationType = operationType;
        this.user = user;
        this.measure = measure;
        this.location = location;
        this.httpDocument = httpDocument;
    }
}
