package com.br.rodrigo.pereira.sensorlogger.model.domain.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCreateData {
    private String name;
    private String city;
    private String province;
    private String country;
}
