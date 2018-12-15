package com.br.rodrigo.pereira.sensorlogger.controllers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.mappers.UserMapper;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserCreateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserDeleteRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserIdentityUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.UserResponse;
import com.br.rodrigo.pereira.sensorlogger.services.UserService;
import com.br.rodrigo.pereira.sensorlogger.services.ValidHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ValidHeaderService validHeaderService;

    @PostMapping(value = "/create")
    public ResponseEntity createUser(
            @RequestBody UserCreateRequest userCreateRequest) {
        userService.createUserRegister(userMapper.userCreateRequestToUserCreateData(userCreateRequest));
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update-general/{username}")
    public ResponseEntity updateGeneralUser(@RequestHeader String token, @PathVariable String username,
                                            @RequestBody UserUpdateRequest userUpdateRequest) {
        validHeaderService.validHeaderPermission(token, 3);
        userService.updateUserGeneralRegister(userMapper.userUpdateDataFromUserUpdate(userUpdateRequest, username));
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update-identity/{username}")
    public ResponseEntity updateUserIdentity(@RequestHeader String token, @PathVariable String username, @RequestBody UserIdentityUpdateRequest userIdentityUpdateRequest) {
        validHeaderService.validHeaderPermission(token, 3);
        userService.updateUserIdentityRegister(userMapper.userIdentityUpdateRequestToUserIdentityUpdateData(userIdentityUpdateRequest, username));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteUser(@RequestHeader String token, @RequestBody UserDeleteRequest userDeleteRequest) {
        validHeaderService.validHeaderPermission(token, 3);
        userService.deleteUser(userMapper.userDeleteRequestToUserDeleteData(userDeleteRequest));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserResponse>> allUserList(@RequestHeader String token) {
        validHeaderService.validHeaderPermission(token, 2);
        List<User> user = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.userListToUserResponseList(user));
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserResponse> singleUser(@RequestHeader String token, @PathVariable String username) {
        validHeaderService.validHeaderPermission(token, 3);
        User user = userService.getUser(username, token);
        return ResponseEntity.ok(userMapper.userToUserResponse(user));
    }
}
