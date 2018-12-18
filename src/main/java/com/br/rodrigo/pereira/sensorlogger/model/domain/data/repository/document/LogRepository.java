package com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {

    @Override
    List<Log> findAll();
}
