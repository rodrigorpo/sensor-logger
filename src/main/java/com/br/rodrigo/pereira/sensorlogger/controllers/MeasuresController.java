package com.br.rodrigo.pereira.sensorlogger.controllers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.MeasureRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.MeasureResponse;
import com.br.rodrigo.pereira.sensorlogger.services.MeasureService;
import com.br.rodrigo.pereira.sensorlogger.services.ValidHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measures")
public class MeasuresController {

    @Autowired
    private ValidHeaderService validHeaderService;

    @Autowired
    private MeasureService measureService;

    @PostMapping(value = "/read")
    public ResponseEntity<MeasureResponse> readMeasure(@RequestHeader String token, @RequestBody MeasureRequest measureRequest) {
        validHeaderService.validHeaderPermission(token, 3);
        return ResponseEntity.ok(measureService.readMeasures(measureRequest));
    }

    @PostMapping(value = "/bomb/on")
    public ResponseEntity<Boolean> turnOnBomb(@RequestHeader String token, @RequestBody MeasureRequest measureRequest) {
        validHeaderService.validHeaderPermission(token, 3);
        return ResponseEntity.ok(measureService.selectBomb("Ligar", measureRequest));
    }

    @PostMapping(value = "/bomb/off")
    public ResponseEntity<Boolean> turnOffBomb(@RequestHeader String token, @RequestBody MeasureRequest measureRequest) {
        validHeaderService.validHeaderPermission(token, 3);
        return ResponseEntity.ok(measureService.selectBomb("Desligar", measureRequest));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<MeasureResponse>> getAllLogs(@RequestHeader String token) {
        User user = validHeaderService.validHeaderPermission(token, 1);
        return ResponseEntity.ok(measureService.getAllMeasures(user));
    }
}
