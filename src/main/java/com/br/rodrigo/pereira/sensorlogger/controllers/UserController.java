package com.br.rodrigo.pereira.sensorlogger.controllers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserCreateRequest;
import com.br.rodrigo.pereira.sensorlogger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> authenticate(
            @RequestBody UserCreateRequest request) {
        userService.createUserRegister(request);
        return ResponseEntity.ok().build();
    }
}
