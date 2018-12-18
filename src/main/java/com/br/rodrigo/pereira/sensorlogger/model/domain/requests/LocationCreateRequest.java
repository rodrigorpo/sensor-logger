package com.br.rodrigo.pereira.sensorlogger.model.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCreateRequest {
    @NotNull
    private String name;
    @NotNull
    private String city;
    @NotNull
    private String province;
    @NotNull
    private String country;
}
