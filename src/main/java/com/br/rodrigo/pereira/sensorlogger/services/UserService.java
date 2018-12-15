package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.UserCreateData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.UserDeleteData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.UserIdentityUpdateData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.UserUpdateData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import com.br.rodrigo.pereira.sensorlogger.util.HttpDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashService hashService;

    @Autowired
    private LogRepository logRepository;

    public void createUserRegister(UserCreateData userCreateData) {

        User userExists = userRepository.findByUsername(userCreateData.getUsername());
        if (userExists != null) {
            BusinessException businessException = new BusinessException("Usuário já existente!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.INSERT, userExists, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }

        User newUser = userRepository.save(new User(
                userCreateData.getName(),
                userCreateData.getCourse(),
                userCreateData.getBirthday(),
                userCreateData.getUsername(),
                hashService.hashPassword(userCreateData.getPassword()),
                userCreateData.getPrivileges(),
                userCreateData.getStatus()));
        logRepository.save(new Log(OperationType.INSERT, newUser, null, null, new HttpDocument(HttpStatus.OK)));
    }

    public void updateUserGeneralRegister(UserUpdateData userUpdateData) {
        User user = verifyUserByUsername(userUpdateData.getUsername(), "Usuário não encontrado!", OperationType.UPDATE);

        if (!user.getPassword().equals(hashService.hashPassword(userUpdateData.getPassword()))) {
            verifyPassword(user, "Senha incorreta para update!");
        }

        User userUpdated = updateUser(user, userUpdateData);
        logRepository.save(new Log(OperationType.UPDATE, userUpdated, null, null, new HttpDocument(HttpStatus.OK)));
    }

    public void updateUserIdentityRegister(UserIdentityUpdateData userIdentityUpdateData) {
        User user = verifyUserByUsername(userIdentityUpdateData.getOldUsername(), "Usuário não encontrado!", OperationType.UPDATE);
        if (!user.getPassword().equals(hashService.hashPassword(userIdentityUpdateData.getOldPassword()))) {
            verifyPassword(user, "Senha incorreta para update!");
        }
        User newUserExists = userRepository.findByUsername(userIdentityUpdateData.getNewUsername());
        if (newUserExists != null) {
            BusinessException businessException = new BusinessException("Username já está em uso!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.UPDATE, user, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        updateUserIdentity(user, userIdentityUpdateData);
        logRepository.save(new Log(OperationType.UPDATE, user, null, null, new HttpDocument(HttpStatus.OK)));
    }

    private void verifyPassword(User user, String exceptionMessage) {
        BusinessException businessException = new BusinessException(exceptionMessage, HttpStatus.UNPROCESSABLE_ENTITY.toString());
        logRepository.save(new Log(OperationType.UPDATE, user, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
        throw businessException;
    }

    public void deleteUser(UserDeleteData userDeleteData) {
        User user = verifyUserByUsername(userDeleteData.getUsername(), "Usuário não existe!", OperationType.DELETE);

        if (!user.getPassword().equals(hashService.hashPassword(userDeleteData.getPassword())) || !user.getBirthday().equals(userDeleteData.getBirthday())) {
            BusinessException businessException = new BusinessException("Não foi possível proceder o delete. Dados incorretos!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.DELETE, user, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        userRepository.delete(user);
        logRepository.save(new Log(OperationType.DELETE, user, null, null, new HttpDocument(HttpStatus.OK)));
    }

    private User updateUser(User user, UserUpdateData userUpdateData) {
        user.setName(userUpdateData.getName());
        user.setCourse(userUpdateData.getCourse());
        user.setBirthday(userUpdateData.getBirthday());
        return userRepository.save(user);
    }

    private User updateUserIdentity(User userIdentity, UserIdentityUpdateData userIdentityUpdateData) {
        userIdentity.setUsername(userIdentityUpdateData.getNewUsername());
        userIdentity.setPassword(hashService.hashPassword(userIdentityUpdateData.getNewPassword()));
        userIdentity.setUserStatus(userIdentityUpdateData.getUserStatus());
        return userRepository.save(userIdentity);
    }

    private User verifyUserByUsername(String username, String failMessage, OperationType operationType) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            BusinessException businessException = new BusinessException(failMessage, HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(operationType, null, null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        return user;
    }

    public List<User> getAllUsers() {
        BusinessException businessException = new BusinessException("Todos os usuários buscados", HttpStatus.OK.toString());
        logRepository.save(new Log(OperationType.SELECT, null, null, null, new HttpDocument(HttpStatus.OK), businessException));
        return userRepository.findAll();
    }

    public User getUser(String username, String token) {
        String[] tokenSplited = token.split("@");

        if (!tokenSplited[0].equals(username)) {
            BusinessException businessException = new BusinessException("Você só pode buscar pelo seu usuário", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.SELECT, userRepository.findByUsername(username), null, null, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        User user = userRepository.findByUsername(username);
        logRepository.save(new Log(OperationType.SELECT, user, null, null, new HttpDocument(HttpStatus.OK)));
        return user;
    }

}
