package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.AuthenticationData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.UserIdentity;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserIdentityRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.UserStatus;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.AuthenticationKeyRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.AuthenticationUserRequest;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

    @Autowired
    private UserIdentityRepository userRepository;

    @Autowired
    private HashService hashService;

    @Autowired
    private LogRepository logRepository;

    public AuthenticationData verifyCredentials(AuthenticationUserRequest authenticationUserRequest) {

        UserIdentity userData = findByUsername(authenticationUserRequest.getUsername());

        String passwordHashed = hashService.hashPassword(authenticationUserRequest.getPassword());

        boolean isValidUsername = userData.getUsername().equals(authenticationUserRequest.getUsername());
        boolean isValidPassword = userData.getPassword().equals(passwordHashed);
        boolean isActive = (userData.getUserStatus() == UserStatus.ACTIVE);

        if (!isValidUsername || !isValidPassword || !isActive) {
            logRepository.save(new Log(OperationType.LOGIN, userData, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Usuário ou senha inválida!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }

        logRepository.save(new Log(OperationType.LOGIN, userData, null, null, Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
        return new AuthenticationData(userData.getUsername(), userData.getPrivileges());
    }

    public UserIdentity findByUsername(String username) {

        UserIdentity userIdentity = userRepository.findByUsername(username);

        if (userIdentity != null) {
            return userIdentity;
        }

        logRepository.save(new Log(OperationType.LOGIN, null, null, null, Long.valueOf(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND.getReasonPhrase()));

        throw new NotFoundException("Usuário não encontrado!", HttpStatus.NOT_FOUND.toString());
    }

    public void breakAuthenticationToken(AuthenticationKeyRequest authenticationKeyRequest) {

    }
}
