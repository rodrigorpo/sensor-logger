package com.br.rodrigo.pereira.sensorlogger.model.domain.mappers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.LocationCreateData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.LocationData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.LocationUpdateData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.LocationCreateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.LocationUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.LocationResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface LocationMapper {

    LocationCreateData locationCreateRequestToLocationCreateData(LocationCreateRequest locationCreateRequest);

    LocationUpdateData locationUpdateRequestToLocationUpdateData(LocationUpdateRequest locationUpdateRequest);

    LocationResponse locationDataToLocationResponse(LocationData locationData);
}
