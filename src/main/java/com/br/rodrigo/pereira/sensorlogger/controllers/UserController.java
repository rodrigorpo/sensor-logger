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
    public ResponseEntity userCreate(
            @RequestBody UserCreateRequest request) {
        userService.createUserRegister(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update-general")
    public ResponseEntity userUpdateGeneral(@RequestBody UserUpdateRequest userUpdateRequest){
        userService.updateUserGeneralRegister(userUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update-identity")
    public ResponseEntity userUpdateIdentity(@RequestBody UserIdentityUpdateRequest userIdentityUpdateRequest){
        userService.updateUserIdentityRegister(userIdentityUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity userDelete(@RequestBody UserDeleteRequest deleteRequest){
        userService.deleteUserIdentity(deleteRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserResponse>> allUserList(@RequestHeader String token){
        validHeaderService.validHeaderPermission(token, 2);
        List<User> user = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.userListToUserResponseList(user));
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserResponse> singleUser(@RequestHeader String token, @PathVariable String username){
        validHeaderService.validHeaderPermission(token, 3);
        User user = userService.getUser(username, token);
        return ResponseEntity.ok(userMapper.userToUserResponse(user));
    }
}
