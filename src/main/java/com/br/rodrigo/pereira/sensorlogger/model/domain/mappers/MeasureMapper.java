package com.br.rodrigo.pereira.sensorlogger.model.domain.mappers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Measure;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.MeasureResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface MeasureMapper {

    default MeasureResponse measureRequestAndRawDataToMeasureResponse(Measure measure) {
        return MeasureResponse.builder()
                .userMinimalData(measure.getUserMinimalData())
                .location(measure.getLocation())
                .airHumidity(measure.getAirHumidity())
                .airTemperature(measure.getAirTemperature())
                .soilHumidity(measure.getSoilHumidity())
                .lighting(measure.getLighting())
                .measureId(measure.getId())
                .build();
    }

    List<MeasureResponse> measureRequestListToMeasureResponseList(List<Measure> measures);
}
