package com.br.rodrigo.pereira.sensorlogger.model.domain.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationUpdateData {
    private String newName;
    private String city;
    private String province;
    private String country;
}
