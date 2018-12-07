package com.br.rodrigo.pereira.sensorlogger.model.domain.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.AuthenticationData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.AuthenticationResponse;

@Mapper(componentModel = "spring")
@Component
public interface AuthenticationMapper {

    AuthenticationResponse authenticationDataToAuthenticationResponse(AuthenticationData authenticationData);
}

