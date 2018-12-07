package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.UserIdentity;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserIdentityRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserCreateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserDeleteRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserIdentityUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserIdentityRepository userIdentityRepository;

    @Autowired
    private HashService hashService;

    @Autowired
    private LogRepository logRepository;

    public void createUserRegister(UserCreateRequest userCreateRequest) {

        UserIdentity userIdentityExists = userIdentityRepository.findByUsername(userCreateRequest.getUsername());
        if (userIdentityExists != null) {
            logRepository.save(new Log(OperationType.INSERT, userIdentityExists, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Usuário já existente!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }

        UserIdentity userIdentity = createUserIdentity(new UserIdentity(
                userCreateRequest.getUsername(),
                hashService.hashPassword(userCreateRequest.getPassword()),
                userCreateRequest.getPrivileges(),
                userCreateRequest.getStatus()));

        createUser(new User(
                userCreateRequest.getName(),
                userCreateRequest.getCourse(),
                userCreateRequest.getBirthday(),
                userIdentity
        ));
        logRepository.save(new Log(OperationType.INSERT, userIdentity, null, null, Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
    }

    public void updateUserRegister(UserUpdateRequest userUpdateRequest){
        UserIdentity userIdentity = userIdentityRepository.findByUsername(userUpdateRequest.getUsername());
        if (userIdentity == null) {
            logRepository.save(new Log(OperationType.UPDATE, userIdentity, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Usuário não existe!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }

        updateUser(userRepository.findByUserIdentity_UserIdentityId(userIdentity.getUserIdentityId()), userUpdateRequest);
        logRepository.save(new Log(OperationType.UPDATE, userIdentity, null, null, Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
    }

    public void updateUserIdentityRegister(UserIdentityUpdateRequest userIdentityUpdateRequest){
        UserIdentity userIdentity = userIdentityRepository.findByUsername(userIdentityUpdateRequest.getOldUsername());
        if (userIdentity == null) {
            logRepository.save(new Log(OperationType.UPDATE, userIdentity, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Usuário não existe!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }
        if(userIdentity.getPassword() != hashService.hashPassword(userIdentityUpdateRequest.getOldPassword())){
            logRepository.save(new Log(OperationType.UPDATE, userIdentity, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Senha incorreta para update!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }
        updateUserIdentity(userIdentity, userIdentityUpdateRequest);
        logRepository.save(new Log(OperationType.UPDATE, userIdentity, null, null, Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
    }

    public void deleteUserIdentity(UserDeleteRequest userDeleteRequest){
        UserIdentity userIdentity = userIdentityRepository.findByUsername(userDeleteRequest.getUsername());
        if (userIdentity == null) {
            logRepository.save(new Log(OperationType.DELETE, userIdentity, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Usuário não existe!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }

        User user = userRepository.findByUserIdentity_UserIdentityId(userIdentity.getUserIdentityId());

        if(user.getBirthday() != userDeleteRequest.getBirthday() || userIdentity.getPassword() != hashService.hashPassword(userDeleteRequest.getPassword())){
            logRepository.save(new Log(OperationType.DELETE, userIdentity, null, null, Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()));
            throw new BusinessException("Usuário não existe!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }

        deleteUser(user, userIdentity);
        logRepository.save(new Log(OperationType.DELETE, userIdentity, null, null, Long.valueOf(HttpStatus.OK.value()), HttpStatus.OK.getReasonPhrase()));
    }

    public UserIdentity createUserIdentity(UserIdentity user) {
        return userIdentityRepository.save(user);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user, UserUpdateRequest userUpdateRequest){
        user.setName(userUpdateRequest.getName());
        user.setCourse(userUpdateRequest.getCourse());
        user.setBirthday(userUpdateRequest.getBirthday());
        userRepository.save(user);
    }

    public void updateUserIdentity(UserIdentity userIdentity, UserIdentityUpdateRequest userIdentityUpdateRequest){
        userIdentity.setUsername(userIdentityUpdateRequest.getNewUsername());
        userIdentity.setPassword(hashService.hashPassword(userIdentityUpdateRequest.getNewPassword()));
        userIdentity.setPrivileges(userIdentityUpdateRequest.getPrivileges());
        userIdentity.setUserStatus(userIdentityUpdateRequest.getUserStatus());
        userIdentityRepository.save(userIdentity);
    }

    public void deleteUser(User user, UserIdentity userIdentity){
        userIdentityRepository.delete(userIdentity);
        userRepository.delete(user);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() ->
            new NotFoundException("Usuário não existente!", HttpStatus.NOT_FOUND.toString()));
    }

    public UserIdentity findByUserIdentityId(String username){
        return userIdentityRepository.findByUsername(username);
    }
}
