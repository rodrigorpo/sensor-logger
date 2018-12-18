package com.br.rodrigo.pereira.sensorlogger.model.domain.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeasureRequest {
    private String user;
    private String location;
}
