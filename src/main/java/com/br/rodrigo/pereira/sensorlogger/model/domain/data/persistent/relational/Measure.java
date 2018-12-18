package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.UserMinimalData;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Document
@Table(name = "measure")
@Data
@NoArgsConstructor
public class Measure implements Serializable {

    @Id
    private String id;
    private BigDecimal airTemperature;
    private BigDecimal airHumidity;
    private Long soilHumidity;
    private Long lighting;
    private UserMinimalData userMinimalData;
    private Location location;

    public Measure(BigDecimal airHumidity, BigDecimal airTemperature, Long soilHumidity, Long lighting, UserMinimalData userMinimalData, Location location) {
        this.airTemperature = airTemperature;
        this.airHumidity = airHumidity;
        this.soilHumidity = soilHumidity;
        this.lighting = lighting;
        this.userMinimalData = userMinimalData;
        this.location = location;
    }

}
