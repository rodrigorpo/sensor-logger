package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserRepository;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.NotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class ValidHeaderService {

    @Autowired
    private UserRepository userRepository;

    public void validHeaderPermission(String header, Integer type){

        String[] headerSplited = header.split("@");
        User user = userRepository.findByUsername(headerSplited[0]);

        if(user == null){
            throw new NotAllowedException("Usuário não existe!", HttpStatus.UNAUTHORIZED.toString());
        }

        if(!headerSplited[1].equalsIgnoreCase(getHeader())){
            throw new NotAllowedException("Chave de acesso não permitida!", HttpStatus.UNAUTHORIZED.toString());
        }

        switch(type){
            case 1:
                if(!user.getUserStatus().toString().equals("OWNER")){
                    throw new NotAllowedException("Privilégios insucificentes para acesar o recurso!", HttpStatus.UNAUTHORIZED.toString());
                }
                break;
            case 2:
                if(!user.getPrivileges().toString().equals("ADMNISTRATOR") && !user.getPrivileges().toString().equals("OWNER")){
                    throw new NotAllowedException("Privilégios insucificentes para acesar o recurso!", HttpStatus.UNAUTHORIZED.toString());
                }
                break;
            default:
                break;
        }
    }

    private String getHeader() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Base64.getEncoder().encodeToString(date.getBytes());
    }
}
