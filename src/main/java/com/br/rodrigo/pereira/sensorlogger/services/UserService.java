package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserCreateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserDeleteRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserIdentityUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import com.br.rodrigo.pereira.sensorlogger.util.HttpDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            BusinessException businessException = new BusinessException("Usuário já existente!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(LocalDateTime.now(), OperationType.INSERT, userExists, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }

        User newUser =  userRepository.save(new User(
                userCreateRequest.getName(),
                userCreateRequest.getCourse(),
                userCreateRequest.getBirthday(),
                userCreateRequest.getUsername(),
                hashService.hashPassword(userCreateRequest.getPassword()),
                userCreateRequest.getPrivileges(),
                userCreateRequest.getStatus()));
        logRepository.save(new Log(LocalDateTime.now(),OperationType.INSERT, newUser, null, null, new HttpDocument(HttpStatus.OK)));
    }

    public void updateUserGeneralRegister(UserUpdateRequest userUpdateRequest){
        User user = verifyUserByUsername(userUpdateRequest.getUsername(), "Usuário não encontrado!", OperationType.UPDATE);

        if(!user.getPassword().equals(hashService.hashPassword(userUpdateRequest.getPassword()))){
            verifyPassword(user, "Senha incorreta para update!");
        }

        User userUpdated = updateUser(user, userUpdateRequest);
        logRepository.save(new Log(LocalDateTime.now(), OperationType.UPDATE, userUpdated, null, null, new HttpDocument(HttpStatus.OK)));
    }

    public void updateUserIdentityRegister(UserIdentityUpdateRequest userIdentityUpdateRequest){
        User user = verifyUserByUsername(userIdentityUpdateRequest.getOldUsername(), "Usuário não existe!", OperationType.UPDATE);
        if(!user.getPassword().equals(hashService.hashPassword(userIdentityUpdateRequest.getOldPassword()))){
            verifyPassword(user, "Senha incorreta para update!");
        }
        User newUserExists = userRepository.findByUsername(userIdentityUpdateRequest.getNewUsername());
        if( newUserExists == null || newUserExists.getUsername().equals(user.getUsername())){
            BusinessException businessException = new BusinessException("Username já está em uso ou é inválido!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(LocalDateTime.now(), OperationType.UPDATE, user, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        updateUserIdentity(user, userIdentityUpdateRequest);
        logRepository.save(new Log(LocalDateTime.now(), OperationType.UPDATE, user, null, null, new HttpDocument(HttpStatus.OK)));
    }

    private void verifyPassword(User user, String exceptionMessage){
        BusinessException businessException = new BusinessException(exceptionMessage, HttpStatus.UNPROCESSABLE_ENTITY.toString());
        logRepository.save(new Log(LocalDateTime.now(), OperationType.UPDATE, user, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
        throw businessException;
    }

    public void deleteUser(UserDeleteRequest userDeleteRequest){
        User user = verifyUserByUsername(userDeleteRequest.getUsername(), "Usuário não existe!", OperationType.DELETE);

        if(!user.getPassword().equals(hashService.hashPassword(userDeleteRequest.getPassword())) || !user.getBirthday().equals(userDeleteRequest.getBirthday())){
            BusinessException businessException = new BusinessException("Não foi possível proceder o delete. Dados incorretos!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(LocalDateTime.now(), OperationType.DELETE, user, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        userRepository.delete(user);
        logRepository.save(new Log(LocalDateTime.now(), OperationType.DELETE, user, null, null, new HttpDocument(HttpStatus.OK)));
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
            BusinessException businessException = new BusinessException(failMessage, HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(LocalDateTime.now(), operationType, null, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        return user;
    }

    public List<User> getAllUsers(){
        BusinessException businessException = new BusinessException("Todos os usuários buscados", HttpStatus.OK.toString());
        logRepository.save(new Log(LocalDateTime.now(), OperationType.SELECT, null, null, null, new HttpDocument(HttpStatus.OK), businessException));
        return userRepository.findAll();
    }

    public User getUser(String username, String token){
        String[] tokenSplited = token.split("@");

        if(!tokenSplited[0].equals(username)){
            BusinessException businessException = new BusinessException("Você só pode buscar pelo seu usuário", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(LocalDateTime.now(), OperationType.SELECT, userRepository.findByUsername(username), null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        User user = userRepository.findByUsername(username);
        logRepository.save(new Log(LocalDateTime.now(), OperationType.SELECT, user, null, null, new HttpDocument(HttpStatus.OK)));
        return user;
    }

}
