package com.br.rodrigo.pereira.sensorlogger.model.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private Long idLocation;
    private String name;
    private String city;
    private String province;
    private String country;
}
