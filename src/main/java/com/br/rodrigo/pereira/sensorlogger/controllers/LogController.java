package com.br.rodrigo.pereira.sensorlogger.controllers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import com.br.rodrigo.pereira.sensorlogger.services.LogService;
import com.br.rodrigo.pereira.sensorlogger.services.ValidHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @Autowired
    private ValidHeaderService validHeaderService;

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteLocation(@PathVariable String id, @RequestHeader String token) {
        validHeaderService.validHeaderPermission(token, 1);
        logService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Log>> findAllLocations(@RequestHeader String token) {
        validHeaderService.validHeaderPermission(token, 1);
        return ResponseEntity.ok(logService.getAllLogs());
    }
}
