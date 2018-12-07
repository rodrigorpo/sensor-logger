package com.br.rodrigo.pereira.sensorlogger.controllers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.AuthenticationData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.mappers.AuthenticationMapper;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.AuthenticationKeyRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.AuthenticationUserRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.AuthenticationResponse;
import com.br.rodrigo.pereira.sensorlogger.services.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticateService authenticateService;

    @Autowired
    private AuthenticationMapper authenticationMapper;


    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationUserRequest request) {
        AuthenticationData authenticationData = authenticateService.verifyCredentials(request);
        return ResponseEntity.ok(authenticationMapper.authenticationDataToAuthenticationResponse(authenticationData));
    }

    @GetMapping(value = "/logout")
    public void authenticate(AuthenticationKeyRequest authenticationKeyRequest) {
        authenticateService.breakAuthenticationToken(authenticationKeyRequest);
    }

}
