package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.Privileges;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.NotAllowedException;
import com.br.rodrigo.pereira.sensorlogger.util.HttpDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class ValidHeaderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogRepository logRepository;

    public User validHeaderPermission(String header, Integer type){

        String[] headerSplit = header.split("@");
        User user = userRepository.findByUsername(headerSplit[0]);

        if(user == null){
            NotAllowedException notAllowedException = new NotAllowedException("Usuário não encontrado!", HttpStatus.UNAUTHORIZED.toString());
            logRepository.save(new Log(LocalDateTime.now(), OperationType.AUTHENTICATE, null, null, null, new HttpDocument(HttpStatus.UNAUTHORIZED), notAllowedException));
            throw notAllowedException;
            }

        if(!headerSplit[1].equalsIgnoreCase(getHeader())){
            NotAllowedException notAllowedException = new NotAllowedException("Chave de acesso não permitida!", HttpStatus.UNAUTHORIZED.toString());
            logRepository.save(new Log(LocalDateTime.now(), OperationType.AUTHENTICATE, null, null, null, new HttpDocument(HttpStatus.UNAUTHORIZED), notAllowedException));
            throw notAllowedException;
        }

        switch(type){
            case 1:
                if(!user.getUserStatus().equals(Privileges.OWNER)){
                    throwsExceptionAndLogIt(user);
                }
                break;
            case 2:
                if(!user.getPrivileges().equals(Privileges.ADMNISTRATOR) && !user.getPrivileges().equals(Privileges.OWNER)){
                    throwsExceptionAndLogIt(user);
                }
                break;
            default:
                break;
        }
        return user;
    }

    private void throwsExceptionAndLogIt(User user){
        NotAllowedException notAllowedException = new NotAllowedException("Privilégios insucificentes para acesar o recurso!", HttpStatus.UNAUTHORIZED.toString());
        logRepository.save(new Log(OperationType.AUTHENTICATE, user, null, null, new HttpDocument(HttpStatus.UNAUTHORIZED), notAllowedException));
        throw notAllowedException;
    }

    private String getHeader() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Base64.getEncoder().encodeToString(date.getBytes());
    }
}
