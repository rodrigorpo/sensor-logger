package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.UserMinimalData;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Measure;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.LocationRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.MeasuresRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational.UserRepository;
import com.br.rodrigo.pereira.sensorlogger.model.domain.enums.OperationType;
import com.br.rodrigo.pereira.sensorlogger.model.domain.mappers.MeasureMapper;
import com.br.rodrigo.pereira.sensorlogger.model.domain.mappers.UserMapper;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.MeasureRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.MeasureResponse;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import com.br.rodrigo.pereira.sensorlogger.util.HttpDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MeasureService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MeasuresRepository measuresRepository;

    @Autowired
    private MeasureMapper measureMapper;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ArduinoService arduinoService;

    @Autowired
    private UserMapper userMapper;

    public MeasureResponse readMeasures(MeasureRequest measureRequest) {
        User user = userRepository.findByUsername(measureRequest.getUser());
        Location location = locationRepository.findByName(measureRequest.getLocation());
        UserMinimalData userMinimalData = userMapper.userToUserMinimalData(user);
        user.setBirthday(null);
        user.setPassword(null);
        user.setCourse(null);
        user.setUserStatus(null);

        if (user == null | location == null) {
            BusinessException businessException = new BusinessException("Usuário ou localidade não encontrada!", HttpStatus.UNPROCESSABLE_ENTITY.toString());
            logRepository.save(new Log(OperationType.INSERT, user, null, location, new HttpDocument(HttpStatus.UNPROCESSABLE_ENTITY), businessException));
            throw businessException;
        }
        String rawData[] = arduinoService.readMeasures().split(";");
        Measure measure = measuresRepository.save(new Measure(new BigDecimal(rawData[0]), new BigDecimal(rawData[1]),
                new Long(rawData[2]), new Long(rawData[3]), userMinimalData, location));

        logRepository.save(new Log(OperationType.INSERT, user, measure, measure.getLocation(), new HttpDocument(HttpStatus.OK)));
        return measureMapper.measureRequestAndRawDataToMeasureResponse(measure);
    }

    public Boolean selectBomb(String option, MeasureRequest measureRequest) {
        Boolean isTurned = false;
        User user = userRepository.findByUsername(measureRequest.getUser());
        Location location = locationRepository.findByName(measureRequest.getLocation());
        if (option.equals("Ligar")) {
            isTurned = arduinoService.selectBomb("Ligar");
            logRepository.save(new Log(OperationType.TURNON, user, null, location, new HttpDocument(HttpStatus.OK)));
        }
        if (option.equals("Desligar")) {
            isTurned = arduinoService.selectBomb("Desligar");
            logRepository.save(new Log(OperationType.TURNOFF, user, null, location, new HttpDocument(HttpStatus.OK)));
        }
        return isTurned;
    }

    public List<MeasureResponse> getAllMeasures(User user) {

        List<Measure> measureList = measuresRepository.findAll();
        logRepository.save(new Log(OperationType.SELECT, user, null, null, new HttpDocument(HttpStatus.OK)));

        return measureMapper.measureRequestListToMeasureResponseList(measureList);
    }
}
