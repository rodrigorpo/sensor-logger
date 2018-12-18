package com.br.rodrigo.pereira.sensorlogger.model.domain.mappers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.AuthenticationData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.AuthenticationRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.AuthenticationResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AuthenticationMapper {

    AuthenticationResponse authenticationDataToAuthenticationResponse(AuthenticationData authenticationData);

    AuthenticationData authenticationRequestToAuthenticationData(AuthenticationRequest authenticationRequest);
}

