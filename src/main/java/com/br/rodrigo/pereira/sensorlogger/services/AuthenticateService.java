package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.AuthenticationData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.UserStatus;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.AuthenticationKeyRequest;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import com.br.rodrigo.pereira.sensorlogger.util.HttpDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashService hashService;

    @Autowired
    private LogRepository logRepository;

    public AuthenticationData verifyCredentials(AuthenticationData authenticationData) {

        User userData = findByUsername(authenticationData.getUsername());

        String passwordHashed = hashService.hashPassword(authenticationData.getPassword());

        boolean isValidUsername = userData.getUsername().equals(authenticationData.getUsername());
        boolean isValidPassword = userData.getPassword().equals(passwordHashed);
        boolean isActive = (userData.getUserStatus() == UserStatus.ACTIVE);

        if (!isValidUsername || !isValidPassword || !isActive) {
            BusinessException businessException = new BusinessException("Senha inválida ou usuário inativo!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.LOGIN, userData, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }

        logRepository.save(new Log(OperationType.LOGIN, userData, null, null, new HttpDocument(HttpStatus.OK)));
        return new AuthenticationData(userData.getUsername(), userData.getPrivileges());
    }

    private User findByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            BusinessException businessException = new BusinessException("Usuário não encontrado!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.LOGIN, null, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }

        return user;
    }

    public void breakAuthenticationToken(AuthenticationKeyRequest authenticationKeyRequest) {

    }
}
