package com.br.rodrigo.pereira.sensorlogger.model.domain.data;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;

import java.math.BigDecimal;

public class MeasureData {
    private User user;
    private Location location;
    private Long measureId;
    private BigDecimal airTemperature;
    private BigDecimal airHumidity;
    private BigDecimal soilHumidity;
    private BigDecimal lighting;
}
