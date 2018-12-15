package com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.document.Log;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {

    Log findByUser(User user);

    Log findByLocation(Location location);

    @Override
    List<Log> findAll();
}
