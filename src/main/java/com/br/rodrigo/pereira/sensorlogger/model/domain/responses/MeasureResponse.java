package com.br.rodrigo.pereira.sensorlogger.model.domain.responses;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.UserMinimalData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MeasureResponse {
    private String measureId;
    private BigDecimal airTemperature;
    private BigDecimal airHumidity;
    private Long soilHumidity;
    private Long lighting;
    private UserMinimalData userMinimalData;
    private Location location;
}
