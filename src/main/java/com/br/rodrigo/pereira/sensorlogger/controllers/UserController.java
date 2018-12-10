package com.br.rodrigo.pereira.sensorlogger.controllers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserCreateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserDeleteRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserIdentityUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.services.UserService;
import com.mongodb.bulk.DeleteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
