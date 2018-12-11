package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserCreateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserDeleteRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserIdentityUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashService hashService;

    @Autowired
    private LogRepository logRepository;

    public void createUserRegister(UserCreateRequest userCreateRequest) {

        User userExists = userRepository.findByUsername(userCreateRequest.getUsername());
        if (userExists != null) {
            logRepository.save(new Log(OperationType.INSERT, userExists, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Usuário já existente!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }

        User newUser =  userRepository.save(new User(
                userCreateRequest.getName(),
                userCreateRequest.getCourse(),
                userCreateRequest.getBirthday(),
                userCreateRequest.getUsername(),
                hashService.hashPassword(userCreateRequest.getPassword()),
                userCreateRequest.getPrivileges(),
                userCreateRequest.getStatus()));
        logRepository.save(new Log(OperationType.INSERT, newUser, null, null, Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
    }

    public void updateUserGeneralRegister(UserUpdateRequest userUpdateRequest){
        User user = verifyUserByUsername(userUpdateRequest.getUsername(), "Usuário não existe!", OperationType.UPDATE);

        if(!user.getPassword().equals(hashService.hashPassword(userUpdateRequest.getPassword()))){
            logRepository.save(new Log(OperationType.UPDATE, user, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Senha incorreta para update!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }

        User userUpdated = updateUser(user, userUpdateRequest);
        logRepository.save(new Log(OperationType.UPDATE, userUpdated, null, null, Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
    }

    public void updateUserIdentityRegister(UserIdentityUpdateRequest userIdentityUpdateRequest){
        User user = verifyUserByUsername(userIdentityUpdateRequest.getOldUsername(), "Usuário não existe!", OperationType.UPDATE);
        if(!user.getPassword().equals(hashService.hashPassword(userIdentityUpdateRequest.getOldPassword()))){
            logRepository.save(new Log(OperationType.UPDATE, user, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Senha incorreta para update!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }
        User newUserExists = userRepository.findByUsername(userIdentityUpdateRequest.getNewUsername());
        if( newUserExists != null && newUserExists != user){
            logRepository.save(new Log(OperationType.UPDATE, user, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Username já está em uso!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }
        updateUserIdentity(user, userIdentityUpdateRequest);
        logRepository.save(new Log(OperationType.UPDATE, user, null, null, Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
    }

    public void deleteUserIdentity(UserDeleteRequest userDeleteRequest){
        User user = verifyUserByUsername(userDeleteRequest.getUsername(), "Usuário não existe!", OperationType.DELETE);

        if(!user.getPassword().equals(hashService.hashPassword(userDeleteRequest.getPassword())) || !user.getBirthday().equals(userDeleteRequest.getBirthday())){
            logRepository.save(new Log(OperationType.DELETE, user, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Não foi possível proceder o delete. Dados incorretos!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }

        userRepository.delete(user);
        logRepository.save(new Log(OperationType.DELETE, user, null, null, Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
    }

    private User updateUser(User user, UserUpdateRequest userUpdateRequest){
        user.setName(userUpdateRequest.getName());
        user.setCourse(userUpdateRequest.getCourse());
        user.setBirthday(userUpdateRequest.getBirthday());
        return userRepository.save(user);
    }

    private User updateUserIdentity(User userIdentity, UserIdentityUpdateRequest userIdentityUpdateRequest){
        userIdentity.setUsername(userIdentityUpdateRequest.getNewUsername());
        userIdentity.setPassword(hashService.hashPassword(userIdentityUpdateRequest.getNewPassword()));
        userIdentity.setUserStatus(userIdentityUpdateRequest.getUserStatus());
        return userRepository.save(userIdentity);
    }

    private User verifyUserByUsername(String username, String failMessage, OperationType operationType){
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logRepository.save(new Log(operationType, user, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException(failMessage, HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }
        return user;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String username, String token){
        String[] tokenSplited = token.split("@");

        if(!tokenSplited[0].equals(username)){
            logRepository.save(new Log(OperationType.SELECT, null, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Você só pode buscar pelo seu usuário", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }

        return userRepository.findByUsername(username);
    }

}
