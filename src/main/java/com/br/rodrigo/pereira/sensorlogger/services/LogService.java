package com.br.rodrigo.pereira.sensorlogger.services;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document.LogRepository;
import com.br.rodrigo.pereira.sensorlogger.model.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public void deleteLog(String id) {
        Log log = logRepository.findById(id).get();
        if (log == null) {
            throw new BusinessException("Log não encontrado", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        }
        logRepository.delete(log);
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }
}
